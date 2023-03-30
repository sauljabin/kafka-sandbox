package kafka.sandbox.cli;

import com.github.javafaker.Faker;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;
import kafka.sandbox.avro.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Slf4j
@Command(name = "producer", description = "Produces supplier messages to the topic")
public class Producer implements Callable<Integer> {

    public static final String TOPIC_TO = "kafka-clients.suppliers";
    private final Properties props;
    private final Faker faker = new Faker();

    @Parameters(
        index = "0",
        description = "Total new supplier messages to produce (default: ${DEFAULT-VALUE})",
        defaultValue = "100"
    )
    private int messages;

    public Producer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        KafkaProducer<String, Supplier> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            Supplier supplier = createNewCustomer();
            ProducerRecord<String, Supplier> record = new ProducerRecord<>(
                TOPIC_TO,
                supplier.getId(),
                supplier
            );
            producer.send(
                record,
                (metadata, exception) -> log.info("Producing message: {}", supplier)
            );
        }

        producer.flush();
        producer.close();

        return CommandLine.ExitCode.OK;
    }

    private Supplier createNewCustomer() {
        return Supplier
            .newBuilder()
            .setId(UUID.randomUUID().toString())
            .setName(faker.name().fullName())
            .setAddress(faker.address().streetAddress())
            .setCountry(faker.country().name())
            .build();
    }
}
