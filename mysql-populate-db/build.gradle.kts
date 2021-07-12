plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    implementation("com.google.guava:guava:30.1-jre")
}

application {
    mainClass.set("kafka.sandbox.App")
}

tasks.test {
    useJUnitPlatform()
}
