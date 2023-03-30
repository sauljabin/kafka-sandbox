package kafka.sandbox;

import kafka.sandbox.cli.NoSqlPopulate;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new NoSqlPopulate());
        System.exit(commandLine.execute(args));
    }
}
