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
/*
allprojects {
    apply(plugin = "org.sonarqube")
}
subprojects {

    sonarqube {
        properties {
            property("sonar.projectKey", "christianGlez100_Quizly")
            property("sonar.projectName", project.name)
            property("sonar.language", "kotlin")
            property("sonar.sourceEncoding", "UTF-8")
            property("sonar.host.url", "https://sonarcloud.io")
            property("sonar.organization","christianglez100")
            property("sonar.login","81e0198c6dfc8ee2c1790af25c633350c1654bbb")
            property("sonar.sources", "src/androidMain/kotlin,src/commonMain/kotlin,src/iosMain/kotlin,src/main/kotlin")
            property("sonar.tests", "src/commonTest/kotlin,src/test/kotlin")
        }
    }
}*/