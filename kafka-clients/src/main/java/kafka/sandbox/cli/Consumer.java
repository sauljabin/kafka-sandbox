package kafka.sandbox.cli;

import kafka.sandbox.avro.Supplier;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import picocli.CommandLine.Command;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Callable;

@Command(name = "consumer", description = "Consumes supplier messages from the topic")
public class Consumer implements Callable<Integer> {
    public static final String TOPIC = "suppliers";
    private final Properties props;

    public Consumer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        KafkaConsumer<String, Supplier> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton(TOPIC));
        try {
            while (true) {
                ConsumerRecords<String, Supplier> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, Supplier> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
                }
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }
}
