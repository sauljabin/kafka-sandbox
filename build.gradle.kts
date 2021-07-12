import org.gradle.internal.os.OperatingSystem

val dockerComposePath = "docker-compose"

val baseCommand = listOf(
    "docker-compose", "-p", project.name,
    "--project-directory", ".",
    "-f", "./kafka/kafka.yml",
    "-f", "./kafka/zookeeper.yml",
    "-f", "./kafka/schema-registry.yml",
    "-f", "./kafka/akhq.yml",
    "-f", "./kafka/kafka-connect.yml",
)

task<Exec>("kafkaUp") {
    description = "Deploys the kafka sandbox cluster"
    group = "Kafka Sandbox"
    commandLine(baseCommand + "up" + "-d")
}

task<Exec>("kafkaDown") {
    description = "Stops the kafka sandbox cluster"
    group = "Kafka Sandbox"
    commandLine(baseCommand + "down")
}

task<Exec>("akhq") {
    description = "Opens AKHQ"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux()) "xdg-open" else "open", "http://localhost:8080")
}
