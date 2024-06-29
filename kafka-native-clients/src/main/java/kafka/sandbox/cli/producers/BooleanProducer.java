package kafka.sandbox.cli.producers;

import kafka.sandbox.cli.Producer;
import org.apache.kafka.common.serialization.BooleanSerializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

public class BooleanProducer extends Producer<Boolean> {

    public BooleanProducer(Properties props) {
        super(props);
    }

    @Override
    public Boolean newMessage() {
        return faker.bool().bool();
    }

    @Override
    public Class<? extends Serializer<?>> getSerializer() {
        return BooleanSerializer.class;
    }
}
