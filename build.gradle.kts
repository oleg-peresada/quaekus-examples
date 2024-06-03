val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.allopen") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"

    id("io.quarkus") version "3.11.0"
    id("org.kordamp.gradle.jandex") version "2.0.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-smallrye-health")

    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-kotlin-serialization")

    //cache
    implementation("io.quarkus:quarkus-cache")
    implementation("io.quarkus:quarkus-redis-client")

    //oidc
    implementation("io.quarkus:quarkus-oidc")

    implementation("io.quarkus:quarkus-messaging")
    implementation("io.smallrye.reactive:mutiny-kotlin")
    implementation("io.smallrye.reactive:smallrye-reactive-messaging-jms")
    implementation("org.apache.activemq:activemq-client:6.1.2")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.quarkusDev {
    compilerOptions {
        compiler("kotlin").args(listOf("-WError"))
    }
}

tasks.quarkusDependenciesBuild {
    dependsOn("jandex")
}