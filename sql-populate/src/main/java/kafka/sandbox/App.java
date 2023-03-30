package kafka.sandbox;

import kafka.sandbox.cli.SqlPopulate;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new SqlPopulate());
        System.exit(commandLine.execute(args));
    }
}
