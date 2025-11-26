package com.sesi.quizly.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sesi.quizly.domain.model.QuestionModel
import com.sesi.quizly.theme.QuizlyTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_down_arrow
import quizly.composeapp.generated.resources.lbl_anser1
import quizly.composeapp.generated.resources.lbl_anser2
import quizly.composeapp.generated.resources.lbl_anser3
import quizly.composeapp.generated.resources.lbl_anser4
import quizly.composeapp.generated.resources.lbl_delete
import quizly.composeapp.generated.resources.lbl_puntos
import quizly.composeapp.generated.resources.lbl_question


@Composable
fun QuestionBlock(index: Int, questions: List<QuestionModel>, question: String = "", isVisible:Boolean, arrow: Float = 0F, onClick: () -> Unit) {

    var questionRemember by remember { mutableStateOf("") }
    var answer1Remember by remember { mutableStateOf("") }
    var answer1PointsRemember by remember { mutableStateOf("")}
    var answer2Remember by remember { mutableStateOf("") }
    var answer2PointsRemember by remember { mutableStateOf("")}
    var answer3Remember by remember { mutableStateOf("") }
    var answer3PointsRemember by remember { mutableStateOf("")}
    var answer4Remember by remember { mutableStateOf("") }
    var answer4PointsRemember by remember { mutableStateOf("")}

    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth()
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
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
                modifier = Modifier.size(45.dp).rotate(degrees = arrow),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clip(RoundedCornerShape(26.dp))) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                TextFieldQ(
                    hint = stringResource(Res.string.lbl_question),
                    value = questionRemember,
                    maxLines = 1,
                    minLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        questionRemember = it
                        questions[index].question = it
                    })
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {

                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser1),
                        value = answer1Remember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            answer1Remember = it
                            questions[index].answer1 = it
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = answer1PointsRemember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            answer1PointsRemember = it
                            questions[index].answer1Points = it
                        })
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {

                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser2),
                        value = answer2Remember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            answer2Remember = it
                            questions[index].answer2 = it
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = answer2PointsRemember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            answer2PointsRemember = it
                            questions[index].answer2Points = it
                        })
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser3),
                        value = answer3Remember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            answer3Remember = it
                            questions[index].answer3 = it
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = answer3PointsRemember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            answer3PointsRemember = it
                            questions[index].answer3Points = it
                        })
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                Box(modifier = Modifier.weight(2f)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_anser4),
                        value = answer4Remember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            answer4Remember = it
                            questions[index].answer4 = it
                        })
                }
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    TextFieldQ(
                        hint = stringResource(Res.string.lbl_puntos),
                        value = answer4PointsRemember,
                        maxLines = 1,
                        minLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            answer4PointsRemember = it
                            questions[index].answer4Points = it
                        })
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                ButtonQCustom(
                    textButton = stringResource(Res.string.lbl_delete),
                    isEnabled = true,
                    textColor = Color.White,
                    buttonContainerColor = MaterialTheme.colorScheme.error,
                    buttonContentColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ) {}
            }

        }
    }
}
