package kafka.sandbox.cli.consumers;

import kafka.sandbox.cli.Consumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.FloatDeserializer;

import java.util.Properties;

public class FloatConsumer extends Consumer<Float> {
    public FloatConsumer(Properties props) {
        super(props);
    }

    @Override
    public Class<? extends Deserializer<?>> getDeserializer() {
        return FloatDeserializer.class;
    }
}
