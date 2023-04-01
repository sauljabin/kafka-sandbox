package kafka.sandbox.cli;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import kafka.sandbox.avro.Supplier;
import kafka.sandbox.grpc.CounterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Slf4j
@Command(name = "streams", description = "Creates and start kafka streams")
public class Streams implements Callable<Integer> {

    public static final String TOPIC_FROM = "kafka-clients.suppliers";
    public static final String TOPIC_TO = "kafka-streams.supplier_counts_by_country";
    private final Properties props;

    public Streams(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        Serde<String> stringSerde = Serdes.String();
        Serde<Long> longSerde = Serdes.Long();

        StreamsBuilder builder = new StreamsBuilder();

        // read from suppliers topic
        KStream<String, Supplier> suppliers = builder.stream(TOPIC_FROM);

        // aggregate the new supplier counts by country
        KTable<String, Long> aggregated = suppliers
                // map the country as key
                .map((key, value) -> new KeyValue<>(value.getCountry(), value))
                .groupByKey()
                // aggregate and materialize the store
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("SupplierCountByCountry"));

        // print results
        aggregated
                .toStream()
                .foreach((key, value) -> log.info("Country = {}, Total supplier counts = {}", key, value));

        // write the results to a topic
        aggregated.toStream().to(TOPIC_TO, Produced.with(stringSerde, longSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.cleanUp();

        // GRPC Server
        Server server = Grpc.newServerBuilderForPort(5050, InsecureServerCredentials.create())
                .addService(new CounterService(streams))
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
