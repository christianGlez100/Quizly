plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "com.sesi.quizly"
version = "1.0.0"
application {
    mainClass.set("com.sesi.quizly.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
    implementation(libs.mysql.connector.java)
    implementation(libs.ktor.koin)
    implementation(libs.hikari)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}