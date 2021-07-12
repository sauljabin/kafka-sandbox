import org.gradle.internal.os.OperatingSystem

val dockerComposePath = "docker-compose"

val baseCommand = listOf(
    "docker-compose", "-p", project.name,
    "--project-directory", ".",
    "-f", "./docker-compose/kafka.yml",
    "-f", "./docker-compose/zookeeper.yml",
    "-f", "./docker-compose/schema-registry.yml",
    "-f", "./docker-compose/akhq.yml",
    "-f", "./docker-compose/kafka-connect.yml",
    "-f", "./docker-compose/mongo.yml",
    "-f", "./docker-compose/mysql.yml",
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
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:8080")
}

task<Exec>("adminer") {
    description = "Opens Adminer SQL GUI"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:9090")
}

task<Exec>("mongoExpess") {
    description = "Opens Mongo Express GUI"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:7070")
}