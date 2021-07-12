import org.gradle.internal.os.OperatingSystem

tasks.register<Exec>("openAkhq") {
    description = "Opens AKHQ UI"
    group = "Kafka Sandbox"
    commandLine(chooseOpenCommandAccordingOS(), "http://localhost:8080")
}

tasks.register<Exec>("openAdminer") {
    description = "Opens the Adminer SQL UI"
    group = "Kafka Sandbox"
    commandLine(chooseOpenCommandAccordingOS(), "http://localhost:9090")
}

tasks.register<Exec>("openMongoExpess") {
    description = "Opens the Mongo Express UI"
    group = "Kafka Sandbox"
    commandLine(chooseOpenCommandAccordingOS(), "http://localhost:7070")
}

tasks.register<Exec>("openSchemaRegistryUi") {
    description = "Opens the Schema Registry UI"
    group = "Kafka Sandbox"
    commandLine(chooseOpenCommandAccordingOS(), "http://localhost:8000")
}

tasks.register<Exec>("openKafkaConnectUi") {
    description = "Opens the Kafka Connect UI"
    group = "Kafka Sandbox"
    commandLine(chooseOpenCommandAccordingOS(), "http://localhost:9000")
}

tasks.register<Exec>("createSandboxNetwork") {
    description = "Creates a Network for the Sandbox"
    group = "Kafka Sandbox"
    commandLine("docker", "network", "create", "--attachable", rootProject.name + "_network")
}

fun chooseOpenCommandAccordingOS() = if (OperatingSystem.current().isLinux) "xdg-open" else "open"