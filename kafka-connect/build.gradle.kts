import com.avast.gradle.dockercompose.RemoveImages.None

plugins {
    id("com.avast.gradle.docker-compose") version "0.14.3"
}

dockerCompose {
    useComposeFiles = listOf("mongo.yml", "mysql.yml", "akhq.yml", "kafka-connect.yml")
    projectName = rootProject.name
    stopContainers = true
    removeContainers = false
    removeImages = None
    removeVolumes = false
    removeOrphans = false
}