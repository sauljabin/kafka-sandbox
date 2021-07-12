import org.gradle.internal.os.OperatingSystem

task<Exec>("openAkhq") {
    description = "Opens AKHQ UI"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:8080")
}

task<Exec>("openAdminer") {
    description = "Opens the Adminer SQL UI"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:9090")
}

task<Exec>("openMongoExpess") {
    description = "Opens the Mongo Express UI"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:7070")
}

task<Exec>("openSchemaRegistryUi") {
    description = "Opens the Schema Registry UI"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:8000")
}

task<Exec>("openKafkaConnectUi") {
    description = "Opens the Kafka Connect UI"
    group = "Kafka Sandbox"
    commandLine(if (OperatingSystem.current().isLinux) "xdg-open" else "open", "http://localhost:9000")
}

task<Exec>("createSandboxNetwork") {
    description = "Creates a Network for the Sandbox"
    group = "Kafka Sandbox"
    commandLine("docker", "network", "create", "--attachable", rootProject.name + "_network")
}