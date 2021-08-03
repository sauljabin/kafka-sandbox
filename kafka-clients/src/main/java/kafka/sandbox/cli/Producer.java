package kafka.sandbox.cli;

import com.github.javafaker.Faker;
import kafka.sandbox.avro.Supplier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;

@Command(name = "producer", description = "Produces supplier messages to the topic")
public class Producer implements Callable<Integer> {

    public static final String TOPIC = "suppliers";
    private final Properties props;
    private final Faker faker = new Faker();

    @Parameters(index = "0", description = "Total new supplier messages to produce (default: ${DEFAULT-VALUE})", defaultValue = "100")
    private int messages;

    public Producer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        KafkaProducer<String, Supplier> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            Supplier supplier = createNewCustomer();
            ProducerRecord<String, Supplier> record = new ProducerRecord<>(TOPIC, supplier);
            producer.send(record, (metadata, exception) -> System.out.println(supplier));
        }

        producer.flush();
        producer.close();
        return CommandLine.ExitCode.OK;
    }

    private Supplier createNewCustomer() {
        return Supplier.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(faker.name().fullName())
                .setAddress(faker.address().streetAddress())
                .build();
    }
}
