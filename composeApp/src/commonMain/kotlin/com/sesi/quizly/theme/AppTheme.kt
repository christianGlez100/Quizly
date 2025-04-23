package com.sesi.quizly.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun QuizlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = QuizlyTypography,
        shapes = QuizlyShapes,
        content = content
    )
}
val QuizlyShapes = Shapes()
/*private val LightColorScheme = lightColorScheme(
    primary = QuizlyColors.Primary,
    secondary = QuizlyColors.Secondary,
    background = QuizlyColors.BackgroundLight,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = QuizlyColors.TextPrimary,
    onBackground = QuizlyColors.TextPrimary,
    onSurface = QuizlyColors.TextPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = QuizlyColors.Primary,
    secondary = QuizlyColors.Secondary,
    background = QuizlyColors.BackgroundDark,
    surface = QuizlyColors.SurfaceDark,
    onPrimary = Color.White,
    onSecondary = QuizlyColors.TextOnDark,
    onBackground = QuizlyColors.TextOnDark,
    onSurface = QuizlyColors.TextOnDark
)*/

private val LightColorScheme = lightColorScheme(
    primary = QuizlyColors.PrimaryLight,
    onPrimary = QuizlyColors.OnPrimaryLight,
    primaryContainer = QuizlyColors.PrimaryContainerLight,
    onPrimaryContainer = QuizlyColors.OnPrimaryContainerLight,
    secondary = QuizlyColors.SecondaryLight,
    onSecondary = QuizlyColors.OnSecondaryLight,
    secondaryContainer = QuizlyColors.SecondaryContainerLight,
    onSecondaryContainer = QuizlyColors.OnSecondaryContainerLight,
    background = QuizlyColors.BackgroundLight,
    onBackground = QuizlyColors.OnBackgroundLight,
    surface = QuizlyColors.SurfaceLight,
    onSurface = QuizlyColors.OnSurfaceLight,
    error = QuizlyColors.ErrorLight,
    onError = QuizlyColors.OnErrorLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = QuizlyColors.PrimaryDark,
    onPrimary = QuizlyColors.OnPrimaryDark,
    primaryContainer = QuizlyColors.PrimaryContainerDark,
    onPrimaryContainer = QuizlyColors.OnPrimaryContainerDark,
    secondary = QuizlyColors.SecondaryDark,
    onSecondary = QuizlyColors.OnSecondaryDark,
    secondaryContainer = QuizlyColors.SecondaryContainerDark,
    onSecondaryContainer = QuizlyColors.OnSecondaryContainerDark,
    background = QuizlyColors.BackgroundDark,
    onBackground = QuizlyColors.OnBackgroundDark,
    surface = QuizlyColors.SurfaceDark,
    onSurface = QuizlyColors.OnSurfaceDark,
    error = QuizlyColors.ErrorDark,
    onError = QuizlyColors.OnErrorDark,
)