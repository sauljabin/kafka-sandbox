plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")

    implementation("info.picocli:picocli:4.6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:5.6")
    implementation("mysql:mysql-connector-java:8.0.25")
    implementation("org.postgresql:postgresql:42.2.23")
    implementation("com.github.javafaker:javafaker:1.0.2")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

application {
    mainClass.set("kafka.sandbox.App")
}

tasks.test {
    useJUnitPlatform()
}