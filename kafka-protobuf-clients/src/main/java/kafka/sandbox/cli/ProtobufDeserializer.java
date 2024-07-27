package kafka.sandbox.cli;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProtobufDeserializer<P extends Message> implements Deserializer<Message> {

    public static final String PROTOBUF_PARSER = "protobuf.parser";
    private Parser<P> parser;

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Map<String, ?> configs, boolean isKey) {
        parser = (Parser<P>) configs.get(PROTOBUF_PARSER);

        if (parser == null) {
            throw new SerializationException("Parser not found. The '%s' config is needed.".formatted(PROTOBUF_PARSER));
        }
    }

    @Override
    public P deserialize(final String topic, final byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return parser.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationException(e);
        }
    }
}