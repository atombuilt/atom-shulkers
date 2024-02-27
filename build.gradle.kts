plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.1.0"
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

group = "com.atombuilt.shulkers"
version = "1.20.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") // Paper repository.
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    implementation("org.bstats", "bstats-bukkit", "3.0.2")
    implementation("com.atombuilt.atomkt", "spigot", "2.0.0")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    processResources {
        expand("version" to project.version)
    }

    runServer {
        minecraftVersion("1.20.1")
    }

    shadowJar {
        // Exclude dependencies included in paper.
        dependencyFilter.apply {
            exclude(dependency("org.jetbrains:annotations"))
        }
        // Relocate bstats to avoid conflicts.
        relocate("org.bstats", "com.atombuilt.shulkers.bstats")
    }
}
