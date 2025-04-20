package com.sesi.quizly.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sesi.quizly.data.dto.QuizDto
import com.sesi.quizly.theme.QuizlyColors
import com.sesi.quizly.theme.QuizlyTheme
import com.sesi.quizly.theme.QuizlyTypography
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.app_name
import quizly.composeapp.generated.resources.compose_multiplatform

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        //QuizlyHeader(title = stringResource(Res.string.app_name)){}
        Body()
    }
}

@Composable
private fun Body(){
    LazyColumn( modifier = Modifier.padding( horizontal = 16.dp, vertical = 16.dp)) {
        items(items = emptyList<QuizDto>()){ quiz ->
            QuizItems(quiz)
        }
    }
}

@Composable
@OptIn(ExperimentalResourceApi::class)
fun QuizItems(quiz:QuizDto) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(20),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            CoilImage(
                imageModel = { Res.getUri("drawable/compose-multipltform.xml") },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier.size(50.dp)
            )
            Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                Text(
                    text = "Decora tu cuarto de ensueño y te diremos con qué vampiro compartes personalidad",
                    color = QuizlyColors.TextPrimary,
                    maxLines = 3,
                    style = QuizlyTypography.bodyMedium
                )
                Text(
                    text = "UFFFF qué buenos personajes",
                    color = QuizlyColors.TextSecondary,
                    style = QuizlyTypography.bodySmall,
                    maxLines = 2
                )
            }
        }

    }
}