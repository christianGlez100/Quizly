package com.sesi.quizly.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sesi.quizly.data.client.request.CreateUserRequest
import com.sesi.quizly.ui.signin.viewmodel.SignInState
import com.sesi.quizly.ui.signin.viewmodel.SignInViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_user

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState
){
    val scope = rememberCoroutineScope()
    val state: SignInState by viewModel.state.collectAsStateWithLifecycle()
    when (state) {

        is SignInState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().testTag("Loading"),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SignInState.FirstState -> {
            bodySignIn(viewModel, false)
        }

        is SignInState.Success -> {

        }

        is SignInState.Error -> {
            val message = (state as SignInState.Error).error
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
            viewModel.restoreState()
        }
    }

}

@Composable
fun bodySignIn(viewModel: SignInViewModel, isError: Boolean, msg:String="") {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var imgUser by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {
            if (isError) {
                scope.launch {
                    snackbarHostState.showSnackbar(msg)
                }
            }

            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp)) {
                    Image(
                        painter = painterResource(Res.drawable.ic_user),
                        contentDescription = "user",
                        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(50.dp)).clickable {

                        }
                    )
                }

                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                ) {
                    OutlinedTextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("User Name") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                }
                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                }

                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                ) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ElevatedButton(onClick = {
                        viewModel.createUser(
                            CreateUserRequest(
                                id = 0,
                                userName = userName,
                                email = email,
                                password = password,
                                userImage = imgUser,
                                userBio = "",
                                isCreator = false
                            )
                        )
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Sigin")
                    }
                }
            }
        }

}