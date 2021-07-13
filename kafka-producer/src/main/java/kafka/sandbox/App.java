package kafka.sandbox;

import com.github.javafaker.Faker;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import kafka.sandbox.avro.Supplier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class App {
    private static final Faker faker = new Faker();
    private static final String topic = "suppliers";
    private static final String bootstrapServers = "localhost:19093,localhost:29093,localhost:39093";
    private static final String schemaRegistryServer = "http://localhost:8081";

    public static void main(String[] args) {
        int totalSuppliers = getTotalSuppliers(args);

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryServer);
        KafkaProducer<String, Supplier> producer = new KafkaProducer<>(props);

        for (int i = 0; i < totalSuppliers; i++) {
            Supplier supplier = createNewCustomer();
            ProducerRecord<String, Supplier> record = new ProducerRecord<>(topic, supplier);
            producer.send(record, (metadata, exception) -> System.out.println(supplier));
        }

        producer.flush();
        producer.close();
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
