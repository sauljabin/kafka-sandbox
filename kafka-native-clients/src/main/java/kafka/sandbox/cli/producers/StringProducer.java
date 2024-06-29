package kafka.sandbox.cli.producers;

import kafka.sandbox.cli.Producer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class StringProducer extends Producer<String> {

    public StringProducer(Properties props) {
        super(props);
    }

    @Override
    public String newMessage() {
        return faker.name().fullName();
    }

    @Override
    public Class<? extends Serializer<?>> getSerializer() {
        return StringSerializer.class;
    }
}

