package kafka.sandbox.cli.consumers;

import kafka.sandbox.cli.Consumer;
import org.apache.kafka.common.serialization.BooleanDeserializer;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Properties;

public class BooleanConsumer extends Consumer<Boolean> {
    public BooleanConsumer(Properties props) {
        super(props);
    }

    @Override
    public Class<? extends Deserializer<?>> getDeserializer() {
        return BooleanDeserializer.class;
    }
}
