package kafka.sandbox.cli;

import kafka.sandbox.avro.CounterMetric;
import kafka.sandbox.avro.Metric;
import kafka.sandbox.avro.MetricType;
import kafka.sandbox.avro.TimerMetric;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
@Command(name = "produce", description = "Produces metrics to topic")
public class Producer implements Callable<Integer> {

    private final Properties props;
    private final Faker faker = new Faker();

    @Parameters(
            index = "1",
            description = "Total messages to produce"
    )
    private int messages;

    @Parameters(
            index = "0",
            description = "Topic name"
    )
    private String topic;

    public Producer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() {
        KafkaProducer<String, Metric> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            Metric metric = newMetric();
            ProducerRecord<String, Metric> record = new ProducerRecord<>(
                    topic,
                    metric.getMetricId(),
                    metric
            );
            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            log.error("Error producing {}", metric, exception);
                            return;
                        }
                        log.info("Producing message: {}", metric);
                    }
            );
        }

        producer.flush();
        producer.close();

        return CommandLine.ExitCode.OK;
    }

    private Metric newMetric() {
        MetricType metricType = faker.bool().bool() ? MetricType.COUNTER : MetricType.TIMER;
        return Metric
                .newBuilder()
                .setMetricId(UUID.randomUUID().toString())
                .setMetricType(metricType)
                .setMetric(metricType.equals(MetricType.COUNTER) ? newCounterMetric() : newTimerMetric())
                .build();
    }

    private CounterMetric newCounterMetric() {
        return CounterMetric
                .newBuilder()
                .setCount(faker.number().numberBetween(10, 20))
                .build();
    }

    private TimerMetric newTimerMetric() {
        return TimerMetric
                .newBuilder()
                .setAvg(faker.number().randomDouble(2, 1, 2))
                .build();
    }
}
