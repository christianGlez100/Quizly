package com.sesi.quizly

import androidx.compose.ui.window.ComposeUIViewController
import com.sesi.quizly.di.appModule
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.context.startKoin
import shared.di.platformModule

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}

fun initKoin() {
    startKoin {
        modules(appModule(Darwin.create()), platformModule())
    }.koin
}