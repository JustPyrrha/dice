import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Year

plugins {
    kotlin("jvm") version "1.6.21"
    id("org.cadixdev.licenser") version "0.6.1"
    `maven-publish`
}

group = "dev.pyrrha"
version = "0.0.0"
val isSnapshot = System.getenv("GITHUB_EVENT_NAME")?.equals("push") ?: true
if(isSnapshot) version = "$version${versionMeta()}"

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
        maven(if(isSnapshot) "https://mvn.pyrrha.dev/snapshots/" else "https://mvn.pyrrha.dev/") {
            name = "maven"
            credentials(PasswordCredentials::class)
            authentication {
                register("basic", BasicAuthentication::class)
            }
        }
    }
}

fun versionMeta(): String {
    var sha = System.getenv("GITHUB_SHA") ?: ""
    var ref = System.getenv("GITHUB_REF") ?: ""
    if(sha.isEmpty() && ref.isEmpty()) return ""

    sha = sha.substring(0, 7)
    ref = ref.substring("refs/heads/".length)

    return "+$ref.$sha"
}