plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.sonarqube)
}

allprojects {
    apply(plugin = "org.sonarqube")
}
subprojects {

    sonarqube {
        properties {
            property("sonar.projectKey", project.name)
            property("sonar.projectName", project.name)
            property("sonar.language", "kotlin")
            property("sonar.sourceEncoding", "UTF-8")
           // property("sonar.host.url", "http://192.168.50.73:9000/")
           // property("sonar.login","sqp_8dfd0795900abb041ce5c8604f9f2e33c10c7473")
            property("sonar.sources", "src/androidMain/kotlin,src/commonMain/kotlin,src/iosMain/kotlin,src/main/kotlin")
            property("sonar.tests", "src/commonTest/kotlin,src/test/kotlin")
        }
    }
}