package kafka.sandbox;

import kafka.sandbox.cli.Consumer;
import kafka.sandbox.cli.KafkaClients;
import kafka.sandbox.cli.Producer;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException {
        new CommandLine(new KafkaClients())
                .addSubcommand(new Producer(getProperties("producer.properties")))
                .addSubcommand(new Consumer(getProperties("consumer.properties")))
                .execute(args);
    }

    private static Properties getProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream(fileName));
        return props;
    }
}
