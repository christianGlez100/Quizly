package com.sesi.quizly.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.sesi.quizly.data.client.request.LogInRequest
import com.sesi.quizly.navigation.Routes
import com.sesi.quizly.ui.components.ButtonQ
import com.sesi.quizly.ui.components.TextFieldQ
import com.sesi.quizly.viewmodel.SignInState
import com.sesi.quizly.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.create_account
import quizly.composeapp.generated.resources.email
import quizly.composeapp.generated.resources.hide_pass
import quizly.composeapp.generated.resources.loading
import quizly.composeapp.generated.resources.password
import quizly.composeapp.generated.resources.show_pass
import quizly.composeapp.generated.resources.sign_in
import shared.preference.PreferenceManager

@Composable
fun LogInScreen(
    viewModel: UserViewModel = koinViewModel(),
    preferenceManager: PreferenceManager,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController
) {
    var userToken by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        preferenceManager.getUserToken().collectLatest {
            userToken = it
        }
    }
    if (userToken != null) {
        navController.navigate(Routes.Profile.route)
    } else {
        val scope = rememberCoroutineScope()
        val state: SignInState by viewModel.state.collectAsStateWithLifecycle()
        when (state) {
            is SignInState.Error -> {
                val message = (state as SignInState.Error).error
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
                viewModel.restoreState()
            }

            is SignInState.FirstState -> {
                bodyLogIn(navController, viewModel)
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
                scope.launch {
                    preferenceManager.saveUserToken((state as SignInState.Success).user.tokenData?.accessToken!!)
                    navController.navigate(Routes.Profile.route)
                }
            }
        }
    }
}

@Composable
fun bodyLogIn(navController: NavHostController, viewModel: UserViewModel) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                TextFieldQ(
                    hint = stringResource(Res.string.email),
                    value = email,
                    maxLines = 1,
                    minLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = {
                        email = it
                        isButtonEnabled = validateFields(email, password)
                    })
            }
            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        isButtonEnabled = validateFields(email, password)
                    },
                    label = { androidx.compose.material.Text(stringResource(Res.string.password), color = MaterialTheme.colorScheme.secondary) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary
                    ),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) stringResource(Res.string.hide_pass) else stringResource(Res.string.show_pass)
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 52.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ButtonQ(
                    onClick = {
                        viewModel.logIn(request = LogInRequest(email, password))
                    }, text = stringResource(Res.string.sign_in),
                    isEnabled = isButtonEnabled
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp).clickable { navController.navigate(Routes.SignIn.route) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(Res.string.create_account),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

private fun validateFields(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}

@Composable
fun LogInScreenPre(){
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                TextFieldQ(
                    hint = "Email",
                    value = "",
                    maxLines = 1,
                    minLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { })
            }
            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("Password", color = MaterialTheme.colorScheme.secondary) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary
                    ),
                    trailingIcon = {
                        val image =
                            if (false) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (false) "Hide password" else "Show password"
                        IconButton(onClick = {  }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    visualTransformation = if (false) VisualTransformation.None else PasswordVisualTransformation()
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 52.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ButtonQ(onClick = {

                }, text = "Sign In")
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Crear una cuenta",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
