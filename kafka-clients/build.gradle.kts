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

    implementation("info.picocli:picocli:4.6.1")
    implementation("org.apache.kafka:kafka-clients:2.7.1")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.apache.avro:avro:1.10.2")
    implementation("io.confluent:kafka-avro-serializer:5.3.0")
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