package com.sesi.quizly.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sesi.quizly.theme.QuizlyColors
import com.sesi.quizly.theme.QuizlyTheme
import com.sesi.quizly.theme.QuizlyTypography
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.resources.painterResource
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_left_arrow

@Composable
fun QuizlyHeader(title: String, isBackVisible:Boolean = false, onBackEvent: () -> Unit) {
    PreComposeApp {
        QuizlyTheme {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary).fillMaxWidth().height(60.dp)) {
                if (isBackVisible) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = { onBackEvent() },) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_left_arrow),
                                contentDescription = "back button",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = title,
                        style = QuizlyTypography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

            }
        }
    }
}