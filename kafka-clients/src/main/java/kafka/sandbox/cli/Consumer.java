package kafka.sandbox.cli;

import kafka.sandbox.avro.Supplier;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

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

        // attach shutdown handler to catch control-c and creating a latch
        CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("consumer-shutdown-hook") {
            @Override
            public void run() {
                consumer.wakeup();
                latch.countDown();
            }
        });

        // infinite loop
        Thread infiniteLoop = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, Supplier> records = consumer.poll(Duration.ofMillis(500));
                    for (ConsumerRecord<String, Supplier> record : records) {
                        System.out.printf("partition = %d, offset = %d, key = %s, value = %s \n", record.partition(), record.offset(), record.key(), record.value());
                    }
                    consumer.commitSync();
                }
            } catch (WakeupException e) {
                e.printStackTrace();
            } finally {
                consumer.close();
            }
        }, "consumer-thread");
        infiniteLoop.start();

        latch.await();

        return CommandLine.ExitCode.OK;
    }
}
