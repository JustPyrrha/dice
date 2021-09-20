import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Year

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.cadixdev.licenser") version "0.6.1"
    `maven-publish`
}

group = "dev.pyrrha"
version = "0.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains:annotations:22.0.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

java {
    withSourcesJar()
    withJavadocJar()
}

license {
    header(project.file("LICENSE_HEADER.txt"))
    include("**/dev/pyrrha/**/*.kt")
    ext {
        set("year", Year.now().value)
        set("holder", "PyrrhaDev")
    }
}

publishing {
    publications {
        register("mavenKotlin", MavenPublication::class) {
            groupId = "dev.pyrrha"
            artifactId = "dice"

            pom {
                description.set("Kotlin TTRPG dice library.")
                url.set("https://cdn.pyrrha.dev/docs/dev/pyrrha/dice/")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://raw.githubusercontent.com/JoeZwet/dice/main/LICENSE")
                    }
                }

                developers {
                    developer {
                        id.set("joezwet")
                        name.set("Pyrrha van der Zwet")
                        email.set("me@joezwet.me")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/JoeZwet/dice.git")
                    developerConnection.set("scm:git:git://github.com/JoeZwet/dice.git")
                    url.set("https://github.com/JoeZwet/dice")
                }
            }
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