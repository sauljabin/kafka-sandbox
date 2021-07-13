package kafka.sandbox;

import kafka.sandbox.avro.Supplier;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class App {
    private static final String topic = "suppliers";

    public static void main(String[] args) throws IOException {
        Properties props = getProperties();
        KafkaConsumer<String, Supplier> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton(topic));

        while (true) {
            ConsumerRecords<String, Supplier> records = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, Supplier> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
            }
            consumer.commitSync();
        }

        //consumer.close();
    }

    private static Properties getProperties() throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream("app.properties"));
        return props;
    }

}
