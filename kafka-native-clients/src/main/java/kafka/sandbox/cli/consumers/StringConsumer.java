package kafka.sandbox.cli.consumers;

import kafka.sandbox.cli.Consumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class StringConsumer extends Consumer<String> {
    public StringConsumer(Properties props) {
        super(props);
    }

    @Override
    public Class<? extends Deserializer<?>> getDeserializer() {
        return StringDeserializer.class;
    }
}
