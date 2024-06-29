package kafka.sandbox.cli;

import kafka.sandbox.cli.consumers.*;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.Properties;
import java.util.concurrent.Callable;

@Slf4j
@Command(name = "consume", description = "Consumes messages from topic")
public class ConsumerCommand implements Callable<Integer> {

    private final Properties props;

    @CommandLine.Parameters(
            index = "0",
            description = "Topic name"
    )
    private String topic;

    @CommandLine.Parameters(
            index = "1",
            description = "Type",
            defaultValue = "string"
    )
    private String type;

    public ConsumerCommand(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() {
        getConsumer().consume(topic);
        return CommandLine.ExitCode.OK;
    }

    private Consumer<?> getConsumer() {
        return switch (type) {
            case "integer" -> new IntegerConsumer(props);
            case "string" -> new StringConsumer(props);
            case "long" -> new LongConsumer(props);
            case "double" -> new DoubleConsumer(props);
            case "float" -> new FloatConsumer(props);
            case "boolean" -> new BooleanConsumer(props);
            default -> throw new RuntimeException("Type not allowed");
        };
    }
}
