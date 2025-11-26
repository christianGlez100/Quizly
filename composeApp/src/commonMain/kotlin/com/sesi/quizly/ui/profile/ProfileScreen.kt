package com.sesi.quizly.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.sesi.quizly.data.Constants
import com.sesi.quizly.data.client.response.CreateUserResponse
import com.sesi.quizly.ui.components.ButtonQ
import com.sesi.quizly.viewmodel.SignInState
import com.sesi.quizly.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_person_circle
import quizly.composeapp.generated.resources.loading
import shared.preference.PreferenceManager
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
fun ProfileScreen(
    viewModel: UserViewModel = koinViewModel(),
    preferenceManager: PreferenceManager?,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,

    ) {
    var userToken by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        preferenceManager?.getUserToken()?.collectLatest {
            userToken = it
        }
    }
    if (userToken != null) {
        val scope = rememberCoroutineScope()
        val state: SignInState by viewModel.state.collectAsStateWithLifecycle()
        when (state) {
            is SignInState.Error -> {
                val message = (state as SignInState.Error).error
                scope.launch {
                    snackBarHostState.showSnackbar(message)
                }
                viewModel.restoreState()
            }

            is SignInState.FirstState -> {
                viewModel.profile(token = userToken!!)
            }

            is SignInState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().testTag(stringResource(Res.string.loading)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SignInState.Success -> {
                val response = (state as SignInState.Success).user
                bodyProfile(response, viewModel, navController)
            }
        }
    }
}

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun bodyProfile(
    response: CreateUserResponse,
    viewModel: UserViewModel,
    navController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp)) {
                if (response.userImage != null){
                    /*val image = viewModel.decodeImage(response.userImage) as ImageBitmap
                    Image(bitmap = image,
                        contentDescription = "user",
                        modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50.dp)).clickable {
                        },
                        contentScale = ContentScale.FillBounds)*/
                     AsyncImage(
                        model = Constants.URL_BASE_ASSETS + response.userImage,
                        contentDescription = "user",
                        modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50.dp)).clickable {
                        },
                        contentScale = ContentScale.FillBounds,
                    )
                } else {
                    Image(
                        painter = painterResource(Res.drawable.ic_person_circle),
                        contentDescription = "user",
                        modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50.dp)).clickable {
                        },
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
                Text(text = response.userName, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = response.userBio ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 5
                )
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item(getSuperHeroes()) {
                        ElevatedCard(
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                    alpha = 0.5f
                                ),
                                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                                    alpha = 0.5f
                                )
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth().height(170.dp).padding(5.dp)
                        ) {
                            Column(
                                modifier = Modifier.wrapContentHeight(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(modifier = Modifier.wrapContentHeight()) {
                                    Image(
                                        painter = painterResource(Res.drawable.ic_person_circle),
                                        contentDescription = "user",
                                        modifier = Modifier.align(Alignment.CenterVertically).fillMaxHeight().width(100.dp),
                                        contentScale = ContentScale.FillBounds,
                                    )
                                    Column(modifier = Modifier.wrapContentHeight().wrapContentWidth()) {
                                        Text(
                                            text = "Elige entre estos objetos random y te diré a qué equipo de Marvel perteneces",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                                            maxLines = 2
                                        )
                                        Text(
                                            text = "Tú encajarías perfecto en los Guardianes de la Galaxia...",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                                            maxLines = 2
                                        )

                                            Text(
                                                text = "10k",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.secondary,
                                                modifier = Modifier.padding(
                                                    top = 9.dp,
                                                    bottom = 8.dp,
                                                    start = 16.dp,
                                                    end = 16.dp
                                                ),
                                                maxLines = 2
                                            )

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.align(alignment = Alignment.BottomCenter).padding(bottom = 16.dp, start = 16.dp, end = 16.dp)){
            ButtonQ(onClick = {
                navController.navigate(com.sesi.quizly.navigation.Routes.CreateQuiz.route)
            }, text = "Crear test", isEnabled = true)
        }
    }
}

fun getSuperHeroes(): List<SuperHero> {
    return listOf(
        SuperHero("Spider-man", "Peter Parker", "Marvel", Res.drawable.ic_person_circle),
        SuperHero("Wolverine", "Logan", "Marvel", Res.drawable.ic_person_circle),
        SuperHero("Batman", "Bruce Wayne", "DC", Res.drawable.ic_person_circle),
        SuperHero("Thor", "Thor Odison", "Marvel", Res.drawable.ic_person_circle),
        SuperHero("Flash", "Barry Allen", "DC", Res.drawable.ic_person_circle),
        SuperHero("Green Lantern", "Alan Scott", "DC", Res.drawable.ic_person_circle),
        SuperHero("Wonder Woman", "Diana", "DC", Res.drawable.ic_person_circle),
    )
}

data class SuperHero(
    var superHero: String,
    var realName: String,
    var publisher: String,
    val icUser: DrawableResource
)
