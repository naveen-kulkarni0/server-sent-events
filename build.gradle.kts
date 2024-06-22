
val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "3.0.0-beta-1"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:${ktor_version}")
    implementation("io.ktor:ktor-server-netty-jvm:${ktor_version}")
    implementation("io.ktor:ktor-server-html-builder:${ktor_version}")
    implementation("io.ktor:ktor-server-sse-jvm:${ktor_version}") // at the time of adding this the sse is in beta
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
