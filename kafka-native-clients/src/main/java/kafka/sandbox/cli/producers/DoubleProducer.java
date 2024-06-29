package kafka.sandbox.cli.producers;

import kafka.sandbox.cli.Producer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

public class DoubleProducer extends Producer<Double> {

    public DoubleProducer(Properties props) {
        super(props);
    }

    @Override
    public Double newMessage() {
        return faker.number().randomDouble(2, 500, 100);
    }

    @Override
    public Class<? extends Serializer<?>> getSerializer() {
        return DoubleSerializer.class;
    }
}
