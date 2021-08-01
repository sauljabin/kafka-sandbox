package kafka.sandbox.cli;

import com.github.javafaker.Faker;
import kafka.sandbox.avro.Supplier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Properties;
import java.util.UUID;

@Command(name = "producer", description = "produces supplier messages to the topic")
public class Producer implements Runnable {

    public static final String TOPIC = "suppliers";
    private final Properties props;
    private final Faker faker = new Faker();

    @Parameters(index = "0", description = "total new supplier messages to produce")
    int messages;

    public Producer(Properties props) {
        this.props = props;
    }

    @Override
    public void run() {
        KafkaProducer<String, Supplier> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            Supplier supplier = createNewCustomer();
            ProducerRecord<String, Supplier> record = new ProducerRecord<>(TOPIC, supplier);
            producer.send(record, (metadata, exception) -> System.out.println(supplier));
        }

        producer.flush();
        producer.close();
    }

    private Supplier createNewCustomer() {
        return Supplier.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(faker.name().fullName())
                .setAddress(faker.address().streetAddress())
                .build();
    }
}
