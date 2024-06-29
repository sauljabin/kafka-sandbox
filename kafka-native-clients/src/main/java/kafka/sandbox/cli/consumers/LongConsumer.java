package kafka.sandbox.cli.consumers;

import kafka.sandbox.cli.Consumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.util.Properties;

public class LongConsumer extends Consumer<Long> {
    public LongConsumer(Properties props) {
        super(props);
    }

    @Override
    public Class<? extends Deserializer<?>> getDeserializer() {
        return LongDeserializer.class;
    }
}
