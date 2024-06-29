package kafka.sandbox.cli.producers;

import kafka.sandbox.cli.Producer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

public class LongProducer extends Producer<Long> {

    public LongProducer(Properties props) {
        super(props);
    }

    @Override
    public Long newMessage() {
        return faker.number().numberBetween(100L, 200L);
    }

    @Override
    public Class<? extends Serializer<?>> getSerializer() {
        return LongSerializer.class;
    }
}
