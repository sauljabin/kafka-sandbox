package kafka.sandbox.cli.producers;

import kafka.sandbox.cli.Producer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

public class IntegerProducer extends Producer<Integer> {

    public IntegerProducer(Properties props) {
        super(props);
    }

    @Override
    public Integer newMessage() {
        return faker.number().numberBetween(100, 200);
    }

    @Override
    public Class<? extends Serializer<?>> getSerializer() {
        return IntegerSerializer.class;
    }
}
