package com.sesi.quizly.ui.quiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.sesi.quizly.ui.components.ButtonQ
import com.sesi.quizly.ui.components.ButtonQCustom
import com.sesi.quizly.ui.components.QuestionBlock
import com.sesi.quizly.ui.components.TextFieldQ
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_user_icon
import quizly.composeapp.generated.resources.lbl_add_question
import quizly.composeapp.generated.resources.lbl_question
import quizly.composeapp.generated.resources.test_description
import quizly.composeapp.generated.resources.test_title

@Composable
fun CreateQuizScreen(){
    Body()
}

@Composable
fun Body(){

    val titleValue by remember { mutableStateOf("") }
    val descriptionValue by remember { mutableStateOf("") }
    val questions = remember { mutableStateOf(listOf(QuestionState(id = 1))) }
    val questionList = remember {  mutableListOf<Question>(Question(id = 1)) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                TextFieldQ(
                    hint = stringResource(Res.string.test_title),
                    value = titleValue,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {})
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Box(modifier = Modifier.weight(2f)){
                    TextFieldQ(
                        hint = stringResource(Res.string.test_description),
                        value = descriptionValue,
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

            questions.value.forEachIndexed { index, questionState ->
                val arrowAngle by animateFloatAsState(
                    targetValue = if (questionState.isVisible) 180f else 0f,
                    label = "arrowAnimation_${questionState.id}"
                )
                QuestionBlock(
                    index = index,
                    questions = questionList,
                    "${stringResource(Res.string.lbl_question)} ${index + 1}",
                    questionState.isVisible,
                    onClick = {
                        questions.value = questions.value.map {
                            if (it.id == questionState.id) {
                                it.copy(isVisible = !it.isVisible)
                            } else {
                                it
                            }
                        }
                    },
                    arrow = arrowAngle
                )
            }
            Column (modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
                ButtonQCustom(
                    textButton = stringResource(Res.string.lbl_add_question),
                    isEnabled = true,
                    textColor = MaterialTheme.colorScheme.onSecondary,
                    buttonContainerColor = MaterialTheme.colorScheme.secondary,
                    buttonContentColor = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ) {
                    val newId = (questions.value.lastOrNull()?.id ?: 0) + 1
                    questionList.add(Question(id = newId))
                    questions.value = questions.value + QuestionState(id = newId)
                }
                ButtonQ({
                    questionList.forEach { Logger.i { it.question } }
                },"Guardar", isEnabled = true )
            }
        }

    }
}

data class Question(
    var id: Int,
    var question: String = "",
    var answer1: String = "",
    var answer1Points: String = "",
    var answer2: String = "",
    var answer2Points: String = "",
    var answer3: String = "",
    var answer3Points: String = "",
    var answer4: String = "",
    var answer4Points: String = "",
    var isComplete: Boolean = false
)

data class QuestionState(
    val id: Int,
    var isVisible: Boolean = false
)