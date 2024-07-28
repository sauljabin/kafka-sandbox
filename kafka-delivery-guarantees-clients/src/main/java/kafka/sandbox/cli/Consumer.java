package kafka.sandbox.cli;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Command(name = "consume", description = "Consumes messages from topic")
public class Consumer implements Callable<Integer> {

    public static final String AT_MOST_ONCE = "at-most-once";
    private static final String AT_LEAST_ONCE = "at-least-once";
    private static final int MAX_RETRIES = 5;

    private KafkaConsumer<String, User> consumer;
    private CountDownLatch latch;
    private final Properties props;
    private final Random random = new Random();

    @Parameters(
            index = "0",
            description = "Topic name"
    )
    private String topic;

    @Option(names = "-s", description = "Semantic. Allowed values: [" + AT_MOST_ONCE + ", " + AT_LEAST_ONCE + "] (default: " + AT_MOST_ONCE + ")", defaultValue = AT_MOST_ONCE)
    private String semantic;

    public Consumer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() throws Exception {
        consumer = new KafkaConsumer<>(props);
        latch = new CountDownLatch(1);
        consumer.subscribe(Collections.singleton(topic));

        // attach shutdown handler to catch control-c and creating a latch
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown, "consumer-shutdown-hook"));

        // infinite loop
        Thread infiniteLoop = new Thread(this::infiniteLoop, "consumer-thread");
        infiniteLoop.start();
        latch.await();

        return CommandLine.ExitCode.OK;
    }

    private void shutdown() {
        consumer.wakeup();
        latch.countDown();
    }

    private void infiniteLoop() {
        try {
            if (semantic.equals(AT_MOST_ONCE)) {
                atMostOnce();
            } else {
                atLeastOnce();
            }
        } catch (WakeupException we) {
            log.info("Shutdown gracefully");
        } finally {
            consumer.close();
            latch.countDown();
        }
    }

    private void atLeastOnce() {
        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(500));

            for (ConsumerRecord<String, User> record : records) {
                try {
                    businessLogic(record);

                    // offsets after the message is processed
                    consumer.commitSync(Map.of(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)));
                } catch (Exception e) {
                    log.error("There was an error processing a message: ", e);

                    // implement recovery and restart (kubernetes), dead-letter queue, etc

                    // throw the exception up
                    throw e;
                }
            }

        }
    }

    private void atMostOnce() {
        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(500));

            // offsets are committed as soon as the message is received
            consumer.commitSync();

            for (ConsumerRecord<String, User> record : records) {
                try {
                    businessLogic(record);
                } catch (Exception e) {
                    // the exception is ignored and the message is lost
                    log.warn("There was an error but it was ignored because this is: " + AT_MOST_ONCE);
                }
            }
        }
    }

    private void businessLogic(ConsumerRecord<String, User> record) {
        log.info("Message was read: partition = {}, offset = {}", record.partition(), record.offset());

        if (random.nextBoolean()) {
            throw new BusinessLogicException("There was an error processing a message");
        }
    }
}
