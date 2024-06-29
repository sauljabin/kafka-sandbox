package kafka.sandbox.cli.consumers;

import kafka.sandbox.cli.Consumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import java.util.Properties;

public class IntegerConsumer extends Consumer<Integer> {
    public IntegerConsumer(Properties props) {
        super(props);
    }

    @Override
    public Class<? extends Deserializer<?>> getDeserializer() {
        return IntegerDeserializer.class;
    }
}
