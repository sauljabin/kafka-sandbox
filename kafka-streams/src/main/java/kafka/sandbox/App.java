package kafka.sandbox;

import java.io.IOException;
import java.util.Properties;

import kafka.sandbox.cli.Count;
import kafka.sandbox.cli.KafkaStreams;
import kafka.sandbox.cli.Streams;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) throws IOException {
        Properties streamsProps = getProperties("streams.properties");

        CommandLine commandLine = new CommandLine(new KafkaStreams())
                .addSubcommand(new Streams(streamsProps)).addSubcommand(new Count());

        System.exit(commandLine.execute(args));
    }

    private static Properties getProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream(fileName));
        return props;
    }
}
