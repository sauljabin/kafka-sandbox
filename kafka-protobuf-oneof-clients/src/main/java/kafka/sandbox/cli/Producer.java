package kafka.sandbox.cli;

import com.google.protobuf.util.Timestamps;
import kafka.sandbox.proto.*;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Properties;
import java.util.concurrent.Callable;

@Slf4j
@Command(name = "produce", description = "Produces messages to topic")
public class Producer implements Callable<Integer> {

    private final Properties props;
    private final Faker faker = new Faker();
    private final Sensor[] sensors = {
            Sensor.newBuilder()
                    .setId("1c107c7d-eb05-44f6-9fdd-4f38aedff16a")
                    .setStatus(SensorStatus.UP)
                    .build(),
            Sensor.newBuilder()
                    .setId("c86612bb-9647-43fd-b1fc-abed8c97091c")
                    .setStatus(SensorStatus.UP)
                    .build()
    };

    @Parameters(
            index = "1",
            description = "Total new messages to produce"
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
        KafkaProducer<String, Measurement> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            Measurement measurement = newMessage();
            ProducerRecord<String, Measurement> record = new ProducerRecord<>(
                    topic,
                    measurement.getSensor().getId(),
                    measurement
            );
            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            log.error("Error producing {}", measurement, exception);
                            return;
                        }
                        log.info("Producing message: {}", measurement);
                    }
            );
        }

        producer.flush();
        producer.close();

        return CommandLine.ExitCode.OK;
    }

    private Measurement newMessage() {
        Measurement.Builder builder = Measurement.newBuilder()
                .setCreatedAt(Timestamps.now());

        if (faker.bool().bool()) {
            builder.setSensor(sensors[0])
                    .setEnvironment(
                            Environment.newBuilder()
                                    .setHumidity(faker.number().randomDouble(2, 10, 100))
                                    .setTemperature(faker.number().randomDouble(2, 10, 30))
                    );
        } else {
            builder.setSensor(sensors[1])
                    .setSpeed(
                            Speed.newBuilder()
                                    .setSpeed(faker.number().randomDouble(2, 0, 120))
                                    .setWheelRpm(faker.number().numberBetween(0, 5000))
                    );
        }

        return builder.build();
    }
}
