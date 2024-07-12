package kafka.sandbox.cli;

import com.google.protobuf.util.Timestamps;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import kafka.sandbox.proto.*;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.BytesSerializer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Properties;
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
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);
        }

        KafkaProducer<String, Invoice> producer = new KafkaProducer<>(props);

        for (int i = 0; i < messages; i++) {
            Invoice invoice = newMessage();
            ProducerRecord<String, Invoice> record = new ProducerRecord<>(
                    topic,
                    invoice.getId(),
                    invoice
            );
            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            log.error("Error producing {}", invoice, exception);
                            return;
                        }
                        log.info("Producing message: {}", invoice);
                    }
            );
        }

        producer.flush();
        producer.close();

        return CommandLine.ExitCode.OK;
    }

    private Invoice newMessage() {
        return Invoice.newBuilder()
                .setId(faker.internet().uuid())
                .setCreatedAt(Timestamps.now())
                .setStatus(InvoiceStatus.forNumber(faker.random().nextInt(InvoiceStatus.values().length-1)))
                .setCustomer(
                        Customer.newBuilder()
                                .setAddress(
                                        Address.newBuilder()
                                                .setCity(faker.address().city())
                                                .setStreet(faker.address().streetAddress())
                                                .setZipCode(faker.address().zipCode())
                                )
                                .setId(faker.internet().uuid())
                                .setFirstName(faker.name().firstName())
                                .setLastName(faker.name().lastName())
                )
                .addProducts(
                        Product.newBuilder()
                                .setId(faker.internet().uuid())
                                .setName(faker.commerce().productName())
                                .setCode(faker.code().isbn10(true))
                                .setPrice(faker.number().randomDouble(2, 10, 200))
                )
                .build();
    }
}
