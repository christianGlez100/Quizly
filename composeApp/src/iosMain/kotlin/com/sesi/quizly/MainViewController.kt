package com.sesi.quizly

import androidx.compose.ui.window.ComposeUIViewController
import com.sesi.quizly.di.appModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(appModule())
    }.koin
}