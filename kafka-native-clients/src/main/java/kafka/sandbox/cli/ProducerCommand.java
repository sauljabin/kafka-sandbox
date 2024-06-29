package kafka.sandbox.cli;

import kafka.sandbox.cli.producers.*;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Properties;
import java.util.concurrent.Callable;

@Slf4j
@Command(name = "produce", description = "Produces messages to topic")
public class ProducerCommand implements Callable<Integer> {

    private final Properties props;


    @Parameters(
            index = "2",
            description = "Total new messages to produce"
    )
    private int messages;

    @Parameters(
            index = "0",
            description = "Topic name"
    )
    private String topic;

    @Parameters(
            index = "1",
            description = "Type",
            defaultValue = "string"
    )
    private String type;


    public ProducerCommand(Properties props) {
        this.props = props;
    }

    @Override
    public Integer call() {
        getProducer().produce(topic, messages);
        return CommandLine.ExitCode.OK;
    }

    private Producer<?> getProducer() {
        return switch (type) {
            case "integer" -> new IntegerProducer(props);
            case "string" -> new StringProducer(props);
            case "long" -> new LongProducer(props);
            case "double" -> new DoubleProducer(props);
            case "float" -> new FloatProducer(props);
            case "boolean" -> new BooleanProducer(props);
            default -> throw new RuntimeException("Type not allowed");
        };
    }

}
