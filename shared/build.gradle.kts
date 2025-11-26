import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    //alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.accompanist.permissions)

            //Koin
            implementation(libs.koin.android)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            //Ktor Client
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.android)

            //DataStore PReference
            implementation(libs.datastore.preference)
            api(libs.datastore.preference.core)
            implementation(libs.datastore)

        }

        commonMain.dependencies {

            implementation(libs.kermit)

            //Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.precompose.koin)
            implementation(libs.koin.viewmodel)

            //Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            //Preference
            implementation(libs.datastore.preference)
            implementation(libs.datastore)

            //Preferences iOS
            implementation(libs.kmm.settings.noarg)
            implementation(libs.kmm.settings)

            implementation(libs.kotlinx.coroutines.core)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
            implementation(libs.koin.test.tools)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test.jvm)
        }
    }
}

android {
    namespace = "com.sesi.quizly.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
