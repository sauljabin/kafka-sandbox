package kafka.sandbox;

import kafka.sandbox.cli.Consumer;
import kafka.sandbox.cli.KafkaClients;
import kafka.sandbox.cli.Producer;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException {
        Properties producerProps = getProperties("producer.properties");
        Properties consumerProps = getProperties("consumer.properties");

        CommandLine commandLine = new CommandLine(new KafkaClients())
                .addSubcommand(new Producer(producerProps))
                .addSubcommand(new Consumer(consumerProps));

        System.exit(commandLine.execute(args));
    }

    private static Properties getProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream(fileName));
        return props;
    }
}
