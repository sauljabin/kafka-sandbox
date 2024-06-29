package kafka.sandbox.cli;

import kafka.sandbox.avro.Supplier;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
@Command(name = "produce", description = "Produces messages to topic")
public class Producer implements Callable<Integer> {

    private final Properties props;
    private final Faker faker = new Faker();

    @Parameters(
            index = "1",
            description = "Total new messages to produce"
    )
    private int messages;

    @Parameters(
            index = "0",
            description = "Topic name"
    )
    private String topic;

    public Producer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() {
        KafkaProducer<String, Supplier> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            Supplier supplier = newMessage();
            ProducerRecord<String, Supplier> record = new ProducerRecord<>(
                    topic,
                    supplier.getId().toString(),
                    supplier
            );
            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            log.error("Error producing {}", supplier, exception);
                            return;
                        }
                        log.info("Producing message: {}", supplier);
                    }
            );
        }

        producer.flush();
        producer.close();

        return CommandLine.ExitCode.OK;
    }

    private Supplier newMessage() {
        return Supplier
                .newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(faker.name().fullName())
                .setAddress(faker.address().streetAddress())
                .setCountry(faker.country().name())
                .build();
    }
}
