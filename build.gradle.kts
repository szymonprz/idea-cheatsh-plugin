plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    id("org.jetbrains.intellij") version "1.9.0"
    id("groovy")
}

group = "pl.szymonprz"
version = "1.3.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.konghq:unirest-java:3.13.10")

    testImplementation(platform("org.spockframework:spock-bom:2.1-groovy-2.5"))
    testImplementation("org.spockframework:spock-core")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.1")
    updateSinceUntilBuild.set(false)
}

tasks {
    compileJava {
        targetCompatibility = "1.8"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        sinceBuild.set("211")
    }

    signPlugin {
        certificateChain.set(System.getenv("JETBRAINS_PLUGIN_CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("JETBRAINS_PLUGIN_PRIVATE_KEY"))
        password.set(System.getenv("JETBRAINS_PLUGIN_PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("JETBRAINS_PLUGIN_PUBLISH_TOKEN"))
    }
}

tasks.test {
    useJUnitPlatform()
}