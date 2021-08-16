import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")

    implementation("io.confluent.ksql:ksqldb-udf:6.2.0")
    implementation("org.apache.kafka:connect-api:2.8.0")
    implementation("org.apache.kafka:kafka_2.13:2.8.0")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("orders-extensions")
    archiveClassifier.set("")
    destinationDirectory.set(file("extensions"))
}
