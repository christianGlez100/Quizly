package com.sesi.quizly

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.sesi.quizly.theme.QuizlyTheme
import com.sesi.quizly.ui.MainScreen
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
fun App() {
    PreComposeApp {
        KoinContext {
            QuizlyTheme {
                setSingletonImageLoaderFactory { context ->
                    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()
                }
                MainScreen()
            }
        }
    }
}