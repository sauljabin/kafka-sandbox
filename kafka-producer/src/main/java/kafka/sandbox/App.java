package kafka.sandbox;

import com.github.javafaker.Faker;
import kafka.sandbox.avro.Supplier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class App {
    private static final Faker faker = new Faker();
    private static final String topic = "suppliers";

    public static void main(String[] args) throws IOException {
        int totalSuppliers = getTotalSuppliers(args);

        Properties props = getProperties();
        KafkaProducer<String, Supplier> producer = new KafkaProducer<>(props);

        for (int i = 0; i < totalSuppliers; i++) {
            Supplier supplier = createNewCustomer();
            ProducerRecord<String, Supplier> record = new ProducerRecord<>(topic, supplier);
            producer.send(record, (metadata, exception) -> System.out.println(supplier));
        }

        producer.flush();
        producer.close();
    }

    private static Properties getProperties() throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream("app.properties"));
        return props;
    }

    private static Supplier createNewCustomer() {
        return Supplier.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(faker.name().fullName())
                .setAddress(faker.address().streetAddress())
                .build();
    }

    private static int getTotalSuppliers(String[] args) {
        return Arrays.asList(args).stream()
                .mapToInt(arg -> Integer.parseInt(arg))
                .findFirst().orElse(15);
    }
}
