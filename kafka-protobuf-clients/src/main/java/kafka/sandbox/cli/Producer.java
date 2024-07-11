package kafka.sandbox.cli;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaJsonSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
@Command(name = "produce", description = "Produces messages to topic")
public class Producer implements Callable<Integer> {

    private final Properties props;
    private final Faker faker = new Faker();

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


    @Option(names = "-s", description = "Use Schema Registry")
    boolean useSchemaRegistry;

    public Producer(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() {
        if (useSchemaRegistry) {
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class);
            props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
        } else {
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        }

        KafkaProducer<String, User> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            User user = newMessage();
            ProducerRecord<String, User> record = new ProducerRecord<>(
                    topic,
                    user.getId(),
                    user
            );
            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            log.error("Error producing {}", user, exception);
                            return;
                        }
                        log.info("Producing message: {}", user);
                    }
            );
        }

        producer.flush();
        producer.close();

        return CommandLine.ExitCode.OK;
    }

    private User newMessage() {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .address(faker.address().streetAddress())
                .age(faker.number().numberBetween(20, 40))
                .build();
    }
}
