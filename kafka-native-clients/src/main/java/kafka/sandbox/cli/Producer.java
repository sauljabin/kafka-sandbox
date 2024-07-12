package kafka.sandbox.cli;

import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;
import java.util.Properties;

@Slf4j
public abstract class Producer<V> {

    protected final Faker faker = new Faker();
    private final KafkaProducer<String, V> producer;

    public Producer(Properties props) {
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getSerializer());
        producer = new KafkaProducer<>(props);
    }

    public void produce(String topic, int message) {
        for (int i = 0; i < message; i++) {
            V value = newMessage();
            ProducerRecord<String, V> record = new ProducerRecord<>(
                    topic,
                    null,
                    String.valueOf(value),
                    value,
                    List.of(
                            new RecordHeader("native", value.getClass().getName().getBytes()),
                            new RecordHeader("value", String.valueOf(value).getBytes())
                    )
            );
            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            log.error("Error producing {}", value, exception);
                            return;
                        }
                        log.info("Producing message: {}", value);
                    }
            );
        }

        producer.flush();
        producer.close();
    }

    public abstract V newMessage();

    public abstract Class<? extends Serializer<?>> getSerializer();
}
