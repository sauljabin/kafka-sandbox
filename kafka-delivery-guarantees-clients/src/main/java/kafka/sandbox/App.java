package kafka.sandbox;

import kafka.sandbox.cli.Consumer;
import kafka.sandbox.cli.KafkaClients;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException {
        Properties consumerProps = getProperties();

        CommandLine commandLine = new CommandLine(new KafkaClients())
                .addSubcommand(new Consumer(consumerProps));

        System.exit(commandLine.execute(args));
    }

    private static Properties getProperties() throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream("consumer.properties"));
        return props;
    }
}
