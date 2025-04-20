package com.sesi.quizly

import androidx.compose.runtime.Composable
import com.sesi.quizly.theme.QuizlyTheme
import com.sesi.quizly.ui.MainScreen
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    PreComposeApp {
        KoinContext {
            QuizlyTheme {
                MainScreen()
            }
        }
    }
}