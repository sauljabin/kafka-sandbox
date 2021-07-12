import com.avast.gradle.dockercompose.RemoveImages.None

plugins {
    id("com.avast.gradle.docker-compose") version "0.14.3"
}

dockerCompose {
    useComposeFiles = listOf("akhq.yml")
    projectName = project.name
    stopContainers = true
    removeContainers = false
    removeImages = None
    removeVolumes = false
    removeOrphans = false
}