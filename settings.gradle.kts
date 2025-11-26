import kotlinx.kover.gradle.aggregation.settings.dsl.minBound
import kotlinx.kover.gradle.plugin.dsl.GroupingEntityType

rootProject.name = "Quizly"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.jetbrains.kotlinx.kover.aggregation") version "0.9.3"
}
dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":server")
include(":shared")

kover {
    enableCoverage()
    reports {
        includedProjects.add(":shared")
        includedProjects.add(":server")
        includedProjects.add("composeApp")
        excludedClasses.add("*.BuildConfig")
        excludedClasses.add("*.ComposableSingletons")
        excludedClasses.add("*ScreenKt*")
        excludedClasses.add("*.di.*")
        excludesAnnotatedBy.add("Generated")

        verify {
            warningInsteadOfFailure = true
            rule {
                name = "custom name - quizly"
                disabled = false
                groupBy = GroupingEntityType.APPLICATION

                minBound(90)

            }
        }
    }
}