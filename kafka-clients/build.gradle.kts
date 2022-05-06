import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
    application
    id("com.github.davidmc24.gradle.plugin.avro-base") version "1.2.0"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")

    implementation("org.apache.kafka:kafka-clients:3.1.0")
    implementation("org.apache.kafka:kafka-streams:3.1.0")
    implementation("org.apache.avro:avro:1.11.0")
    implementation("io.confluent:kafka-avro-serializer:7.1.1")
    implementation("io.confluent:kafka-streams-avro-serde:7.1.1")
    implementation("org.rocksdb:rocksdbjni:7.1.2")

    implementation("info.picocli:picocli:4.6.1")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.slf4j:slf4j-simple:1.7.30")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

application {
    mainClass.set("kafka.sandbox.App")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<GenerateAvroJavaTask>("generateAvro") {
    description = "Generates Avro Java Classes"
    group = "Kafka Sandbox"
    setSource("src/main/avro")
    setOutputDir(file("src/main/java"))
}