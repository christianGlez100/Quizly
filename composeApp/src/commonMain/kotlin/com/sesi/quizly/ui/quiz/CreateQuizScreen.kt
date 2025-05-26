package com.sesi.quizly.ui.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sesi.quizly.ui.components.TextFieldQ
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_user_icon
import quizly.composeapp.generated.resources.test_title

@Composable
fun CreateQuizScreen(){
    Body()
}

@Composable
fun Body(){
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)) {
                TextFieldQ(
                    hint = stringResource(Res.string.test_title),
                    value = "",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {})
            }
            Row(modifier = Modifier.wrapContentHeight().align(Alignment.CenterHorizontally).padding(top = 16.dp).weight(3f)) {
                Box(modifier = Modifier.weight(2f)){
                    TextFieldQ(
                        hint = stringResource(Res.string.test_title),
                        value = "",
                        maxLines = 4,
                        minLines = 4,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {})
                }
                Box(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)){
                    Image(
                        painter = painterResource(Res.drawable.ic_user_icon),
                        contentDescription = "user",
                        modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50.dp)).clickable {

                        },
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }

            }

        }
    }
}