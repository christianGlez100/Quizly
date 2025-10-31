
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
    alias(libs.plugins.ktor.kover)
}


kover {
    reports {
        verify {
            rule {
                minBound(90)
            }
        }

        filters {
            excludes {
                packages("*.generated.*")
                packages("*di*")
                classes("*ComposableSingletons*")
                annotatedBy("androidx.compose.runtime.Composable")
            }
        }

        total {
            // Reporte XML para SonarCloud
            xml {
                onCheck = false
                xmlFile = layout.buildDirectory.file("reports/kover/report.xml")
            }

            // Reporte HTML local
            html {
                onCheck = false
                title.set("Kover Coverage Report")
                htmlDir = layout.buildDirectory.dir("reports/kover/html")
            }
        }
    }
}