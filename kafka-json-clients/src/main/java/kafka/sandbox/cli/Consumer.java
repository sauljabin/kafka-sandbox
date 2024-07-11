package kafka.sandbox.cli;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaJsonDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.errors.WakeupException;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Command(name = "consume", description = "Consumes messages from topic")
public class Consumer implements Callable<Integer> {

    private final Properties props;

    @CommandLine.Parameters(
            index = "0",
            description = "Topic name"
    )
    private String topic;

    @CommandLine.Option(names = "-s", description = "Use Schema Registry")
    boolean useSchemaRegistry;

    public Consumer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        if (useSchemaRegistry) {
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonSchemaDeserializer.class);
            props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
        } else {
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class);
        }

        KafkaConsumer<String, User> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton(topic));

        // attach shutdown handler to catch control-c and creating a latch
        CountDownLatch latch = new CountDownLatch(1);
        Runtime
                .getRuntime()
                .addShutdownHook(
                        new Thread("consumer-shutdown-hook") {
                            @Override
                            public void run() {
                                consumer.wakeup();
                                latch.countDown();
                            }
                        }
                );

        // infinite loop
        Thread infiniteLoop = new Thread(
                () -> {
                    try {
                        while (true) {
                            ConsumerRecords<String, User> records = consumer.poll(
                                    Duration.ofMillis(500)
                            );
                            for (ConsumerRecord<String, User> record : records) {
                                log.info(
                                        "Consumed message: topic = {}, partition = {}, offset = {}, key = {}, value = {}",
                                        record.topic(),
                                        record.partition(),
                                        record.offset(),
                                        record.key(),
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
                    }
                },
                "consumer-thread"
        );

        infiniteLoop.start();
        latch.await();

        return CommandLine.ExitCode.OK;
    }
}
