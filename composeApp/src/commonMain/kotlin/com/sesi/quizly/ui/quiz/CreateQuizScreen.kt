package com.sesi.quizly.ui.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sesi.quizly.theme.QuizlyTheme
import com.sesi.quizly.theme.QuizlyTypography
import com.sesi.quizly.ui.components.TextFieldQ
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_down_arrow
import quizly.composeapp.generated.resources.ic_user_icon
import quizly.composeapp.generated.resources.lbl_anser1
import quizly.composeapp.generated.resources.lbl_anser2
import quizly.composeapp.generated.resources.lbl_anser3
import quizly.composeapp.generated.resources.lbl_anser4
import quizly.composeapp.generated.resources.lbl_puntos
import quizly.composeapp.generated.resources.lbl_question
import quizly.composeapp.generated.resources.test_description
import quizly.composeapp.generated.resources.test_title

@Composable
fun CreateQuizScreen(){
    Body()
}

@Composable
fun Body(){
    var question1Visible by remember { mutableStateOf(false) }
    var question2Visible by remember { mutableStateOf(false) }
    var question3Visible by remember { mutableStateOf(false) }
    var question4Visible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                TextFieldQ(
                    hint = stringResource(Res.string.test_title),
                    value = "",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {})
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Box(modifier = Modifier.weight(2f)){
                    TextFieldQ(
                        hint = stringResource(Res.string.test_description),
                        value = "",
                        maxLines = 4,
                        minLines = 4,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {})
                }
                Box(modifier = Modifier.weight(1f)){
                    Image(
                        painter = painterResource(Res.drawable.ic_user_icon),
                        contentDescription = "user",
                        modifier = Modifier.size(120.dp).align(Alignment.Center).clip(RoundedCornerShape(50.dp)).clickable {

                        },
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
            Question("${stringResource(Res.string.lbl_question)} 1", question1Visible, onClick = {question1Visible = !question1Visible})
            Question("${stringResource(Res.string.lbl_question)} 2", question2Visible, onClick = {question2Visible = !question2Visible})
            Question("${stringResource(Res.string.lbl_question)} 3", question3Visible, onClick = {question3Visible = !question3Visible})
            Question("${stringResource(Res.string.lbl_question)} 4", question4Visible, onClick = {question4Visible = !question4Visible})
        }

    }
}

@Composable
private fun Question(question: String = "", isVisible:Boolean, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        .clickable {
            onClick()
    },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = question,
                style = QuizlyTypography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2
            )
        }
        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                painter = painterResource(Res.drawable.ic_down_arrow),
                contentDescription = "row",
                modifier = Modifier.size(45.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            // Slide in from the bottom
            initialOffsetY = { fullHeight -> fullHeight }
        ),
        exit = slideOutVertically(
            // Slide out to the bottom
            targetOffsetY = { fullHeight -> fullHeight }
        )
    ) {
        Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp).background(
            MaterialTheme.colorScheme.primaryContainer
        )) {
            TextFieldQ(
                hint = stringResource(Res.string.lbl_question),
                value = "",
                maxLines = 1,
                minLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                })
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser1),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser2),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser3),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser4),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = "",
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                        })
                }
            }

        }
    }
}