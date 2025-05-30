package com.sesi.quizly.ui.signin

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.sesi.quizly.data.client.request.CreateUserRequest
import com.sesi.quizly.navigation.Routes
import com.sesi.quizly.ui.components.AlertMessageDialog
import com.sesi.quizly.ui.components.ButtonQ
import com.sesi.quizly.ui.components.ImageSourceOptionDialog
import com.sesi.quizly.ui.components.TextFieldQ
import com.sesi.quizly.ui.signin.viewmodel.SignInState
import com.sesi.quizly.ui.signin.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.email
import quizly.composeapp.generated.resources.hide_pass
import quizly.composeapp.generated.resources.ic_person_circle
import quizly.composeapp.generated.resources.ic_user
import quizly.composeapp.generated.resources.ic_user_icon
import quizly.composeapp.generated.resources.loading
import quizly.composeapp.generated.resources.password
import quizly.composeapp.generated.resources.show_pass
import quizly.composeapp.generated.resources.sign_in
import quizly.composeapp.generated.resources.user_name
import shared.PermissionCallback
import shared.PermissionStatus
import shared.PermissionType
import shared.createPermissionsManager
import shared.preference.PreferenceManager
import shared.rememberCameraManager
import shared.rememberGalleryManager
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun SignInScreen(
    viewModel: UserViewModel = koinViewModel(),
    preferenceManager: PreferenceManager?,
    snackbarHostState: SnackbarHostState?,
    navController: NavHostController?
) {
    val scope = rememberCoroutineScope()
    val state: SignInState by viewModel.state.collectAsStateWithLifecycle()
    when (state) {

        is SignInState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().testTag(stringResource(Res.string.loading)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SignInState.FirstState -> {
            bodySignIn(viewModel, false)
        }

        is SignInState.Success -> {
            LaunchedEffect(Unit) {
                preferenceManager?.saveUserToken((state as SignInState.Success).user.tokenData?.accessToken!!)
            }
            navController?.navigate(Routes.LogIn.route)
        }

        is SignInState.Error -> {
            val message = (state as SignInState.Error).error
            scope.launch {
                snackbarHostState?.showSnackbar(message)
            }
            viewModel.restoreState()
        }
    }

}

@Composable
fun bodySignIn(viewModel: UserViewModel, isError: Boolean, msg: String = "") {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    // camera n gallery
    var launchSetting by remember { mutableStateOf(value = false) }
    val coroutineScope = rememberCoroutineScope()
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var imageByte by remember { mutableStateOf<ByteArray?>(null) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera = true
                        PermissionType.GALLERY -> launchGallery = true
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }


    })
    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            imageByte = withContext(Dispatchers.Default) {
                it?.toByteArray()
            }
            imageBitmap = bitmap
        }
    }
    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            imageByte = withContext(Dispatchers.Default) {
                it?.toByteArray()
            }
            imageBitmap = bitmap
        }
    }

    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }
    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery = false
    }
    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera = false
    }
    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }
    if (permissionRationalDialog) {
        AlertMessageDialog(
            title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true

            },
            onNegativeClick = {
                permissionRationalDialog = false
            })

    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        if (isError) {
            scope.launch {
                snackbarHostState.showSnackbar(msg)
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp)) {
                if (imageBitmap == null) {
                    Image(
                        painter = painterResource(Res.drawable.ic_user_icon),
                        contentDescription = "user",
                        modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50.dp)).clickable {
                            imageSourceOptionDialog = true
                        },
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                } else {
                    Image(
                        bitmap = imageBitmap!!,
                        contentDescription = "user",
                        modifier = Modifier.size(120.dp).clip(RoundedCornerShape(50.dp)).clickable {
                            imageSourceOptionDialog = true
                        },
                        contentScale = ContentScale.FillBounds
                        )
                }
            }

            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                TextFieldQ(
                    hint = stringResource(Res.string.user_name),
                    value = userName,
                    maxLines = 1,
                    minLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        userName = it
                        isButtonEnabled = validateFields(userName, email, password)
                    })
            }
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
                        isButtonEnabled = validateFields(userName, email, password)
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
                        isButtonEnabled = validateFields(userName, email, password)
                    },
                    label = { Text(stringResource(Res.string.password), color = MaterialTheme.colorScheme.secondary) },
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

                        viewModel.createUser(
                            CreateUserRequest(
                                id = 0,
                                userName = userName,
                                email = email,
                                password = password,
                                userImage = imageByte,
                                userImageName = "${userName}.jpg",
                                userBio = "",
                                isCreator = false
                            )
                        )
                    }, text = stringResource(Res.string.sign_in),
                    isEnabled = isButtonEnabled
                )
            }
        }
    }
}

private fun validateFields(userName: String, email: String, password: String): Boolean {
    return userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
}