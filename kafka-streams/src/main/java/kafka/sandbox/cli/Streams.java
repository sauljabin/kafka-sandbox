package kafka.sandbox.cli;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import kafka.sandbox.avro.Supplier;
import kafka.sandbox.grpc.CountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Command(name = "streams", description = "Creates and start kafka streams")
public class Streams implements Callable<Integer> {

    private final Properties props;
    @CommandLine.Parameters(
            index = "0",
            description = "Topic source"
    )
    private String source;
    @CommandLine.Parameters(
            index = "1",
            description = "Topic target"
    )
    private String sink;

    public Streams(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        StreamsBuilder builder = new StreamsBuilder();

        // read from suppliers topic
        KStream<String, Supplier> suppliers = builder.stream(source);

        // aggregate the new supplier counts by country
        KTable<String, Long> aggregated = suppliers
                // map the country as key
                .map((key, value) -> new KeyValue<>(value.getCountry(), value))
                .groupByKey()
                // aggregate and materialize the store
                .count(Materialized.as("SupplierCountByCountry"));

        // write the results to a topic
        aggregated.toStream()
                // print results
                .peek((key, value) -> log.info("Country = {}, Total supplier counts = {}", key, value))
                // publish results
                .to(sink, Produced.with(Serdes.String(), Serdes.Long()));

        // build the topology
        Topology topology = builder.build();

        // create kafka streams object
        KafkaStreams streams = new KafkaStreams(topology, props);
        streams.cleanUp();

        // GRPC Server
        Server server = Grpc.newServerBuilderForPort(5050, InsecureServerCredentials.create())
                .addService(new CountService(streams))
                .build();

        // attach shutdown handler to catch control-c and creating a latch
        CountDownLatch latch = new CountDownLatch(1);
        Runtime
                .getRuntime()
                .addShutdownHook(
                        new Thread("consumer-shutdown-hook") {
                            @Override
                            public void run() {
                                server.shutdownNow();
                                streams.close();
                                latch.countDown();
                            }
                        });

        streams.start();
        server.start();
        latch.await();

        return CommandLine.ExitCode.OK;
    }
}
