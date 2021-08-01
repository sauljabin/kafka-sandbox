package kafka.sandbox;

import kafka.sandbox.cli.JdbcPopulateDb;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new JdbcPopulateDb());
        System.exit(commandLine.execute(args));
    }

}
