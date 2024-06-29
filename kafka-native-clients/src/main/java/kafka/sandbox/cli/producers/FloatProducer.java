package kafka.sandbox.cli.producers;

import kafka.sandbox.cli.Producer;
import org.apache.kafka.common.serialization.FloatSerializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

public class FloatProducer extends Producer<Float> {

    public FloatProducer(Properties props) {
        super(props);
    }

    @Override
    public Float newMessage() {
        return (float) faker.number().randomDouble(2, 500, 100);
    }

    @Override
    public Class<? extends Serializer<?>> getSerializer() {
        return FloatSerializer.class;
    }
}
