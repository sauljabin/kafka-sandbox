package kafka.sandbox.cli;

import kafka.sandbox.proto.Measurement;
import lombok.extern.slf4j.Slf4j;
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

    public Consumer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        KafkaConsumer<String, Measurement> consumer = new KafkaConsumer<>(props);
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
                            ConsumerRecords<String, Measurement> records = consumer.poll(
                                    Duration.ofMillis(500)
                            );
                            for (ConsumerRecord<String, Measurement> record : records) {
                                log.info(
                                        "Consumed message ({}) : topic = {}, partition = {}, offset = {}, key = {}, value = {}",
                                        // this way we know what value is inside the oneof
                                        record.value().getValueCase(),
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
