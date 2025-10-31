import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.sonarqube)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

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
            implementation(compose.runtime)
            api(compose.foundation)
            api(compose.animation)
            implementation(compose.material)
            api(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.shared)

            // Navigation PreCompose
            api(libs.precompose)
            //Viewmodel
            api(libs.precompose.viewmodel)

            api(compose.material3)

            //landscapist-coil3
            //implementation(libs.landscapist.coil)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.compose.material3)

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

        iosMain.dependencies {
            //Ktor Client
            implementation(libs.ktor.client.darwin)
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
    namespace = "com.sesi.quizly"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.sesi.quizly"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.exifinterface)
    debugImplementation(compose.uiTooling)
}

