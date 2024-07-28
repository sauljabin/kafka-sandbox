package kafka.sandbox.cli;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.Deserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
public abstract class Consumer<V> {

    private final KafkaConsumer<String, V> consumer;

    public Consumer(Properties props) {
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getDeserializer());
        consumer = new KafkaConsumer<>(props);
    }

    public void consume(String topic) {
        consumer.subscribe(Collections.singleton(topic));
        CountDownLatch latch = new CountDownLatch(1);
        Runtime
                .getRuntime()
                .addShutdownHook(
                        new Thread(() -> {
                            consumer.wakeup();
                            latch.countDown();
                        }, "consumer-shutdown-hook")
                );

        Thread infiniteLoop = new Thread(
                () -> {
                    try {
                        while (true) {
                            ConsumerRecords<String, V> records = consumer.poll(
                                    Duration.ofMillis(500)
                            );
                            for (ConsumerRecord<String, V> record : records) {
                                log.info(
                                        "Consumed message: topic = {}, partition = {}, offset = {}, value = {}",
                                        record.topic(),
                                        record.partition(),
                                        record.offset(),
                                        record.value()
                                );
                            }
                            consumer.commitSync();
                        }
                    } catch (RecordDeserializationException rde) {
                        log.warn("{}", rde.getMessage());
                    } catch (WakeupException we) {
                        log.info("Shutdown gracefully");
                    } finally {
                        consumer.close();
                        latch.countDown();
                    }
                },
                "consumer-thread"
        );

        infiniteLoop.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Class<? extends Deserializer<?>> getDeserializer();
}
