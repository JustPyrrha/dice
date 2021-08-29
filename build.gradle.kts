import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Year

plugins {
    kotlin("jvm") version "1.5.30"
    id("org.cadixdev.licenser") version "0.6.1"
    `maven-publish`
}

group = "dev.pyrrha"
version = "0.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

license {
    header(project.file("LICENSE_HEADER.txt"))
    include("**/dev/pyrrha/**/*.kt")
    ext {
        set("year", Year.now().value)
        set("holder", "Pyrrha")
    }
}

publishing {
    publications {
        register("mavenKotlin", MavenPublication::class) {
            groupId = "dev.pyrrha"
            artifactId = "dice"
        }
    }
    repositories {
        maven {
            setUrl("s3://cdn.pyrrha.dev/maven")
            authentication {
                register("awsIm", AwsImAuthentication::class)
            }
        }
    }
}