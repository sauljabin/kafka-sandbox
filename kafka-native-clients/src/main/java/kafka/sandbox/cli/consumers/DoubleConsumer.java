package kafka.sandbox.cli.consumers;

import kafka.sandbox.cli.Consumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.DoubleDeserializer;

import java.util.Properties;

public class DoubleConsumer extends Consumer<Double> {
    public DoubleConsumer(Properties props) {
        super(props);
    }

    @Override
    public Class<? extends Deserializer<?>> getDeserializer() {
        return DoubleDeserializer.class;
    }
}
