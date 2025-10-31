import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.ktor.kover)
    application
}

group = "com.sesi.quizly"
version = "1.0.0"
application {
    mainClass = "com.sesi.quizly.MainAppKt"
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.java.jwt)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
    implementation(libs.mysql.connector.java)
    implementation(libs.ktor.koin)
    implementation(libs.hikari)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.mockito.kotlin)
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("quizly")
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())

    manifest {
        attributes(
            mapOf("Main-Class" to application.mainClass.get())
        )
    }
}
tasks.named("build") {
    dependsOn(tasks.named("shadowJar"))
}