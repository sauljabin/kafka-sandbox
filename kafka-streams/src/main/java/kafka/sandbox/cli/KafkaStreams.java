package kafka.sandbox.cli;

import static picocli.CommandLine.ParameterException;
import static picocli.CommandLine.Spec;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

@Command(name = "kafka-streams", description = "Instantiate a kafka stream example", synopsisSubcommandLabel = "COMMAND")
public class KafkaStreams implements Runnable {

    @Spec
    private CommandSpec spec;

    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Missing required subcommand");
    }
}
