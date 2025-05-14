package com.sesi.quizly.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sesi.quizly.data.dto.QuizDto
import com.sesi.quizly.theme.QuizlyTypography
import org.jetbrains.compose.resources.ExperimentalResourceApi
import quizly.composeapp.generated.resources.Res

@Composable
fun HomeScreen(snackbarHostState: SnackbarHostState) {
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally) {
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

            AsyncImage(
                model = "",
                contentDescription = "user",
                modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50.dp)).clickable {
                },
                contentScale = ContentScale.FillBounds,
            )
            Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                Text(
                    text = "Decora tu cuarto de ensueño y te diremos con qué vampiro compartes personalidad",
                    maxLines = 3,
                    style = QuizlyTypography.bodyMedium
                )
                Text(
                    text = "UFFFF qué buenos personajes",
                    style = QuizlyTypography.bodySmall,
                    maxLines = 2
                )
            }
        }

    }
}