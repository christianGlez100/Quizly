package com.sesi.quizly.presenter.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesi.quizly.data.client.request.CreateUserRequest
import com.sesi.quizly.data.client.request.LogInRequest
import com.sesi.quizly.data.client.response.CreateUserResponse
import com.sesi.quizly.data.repository.UserAction
import com.sesi.quizly.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shared.Base64Converter

sealed class SignInState {
    object Loading : SignInState()
    object FirstState : SignInState()
    data class Success(val user: CreateUserResponse) : SignInState()
    data class Error(val error: String) : SignInState()
}

class UserViewModel(
    private val userRepository: UserRepository,
    private val base64Converter: Base64Converter
) : ViewModel(), UserAction {

    private val _state = MutableStateFlow<SignInState>(SignInState.FirstState)
    val state: StateFlow<SignInState> = _state
    val userCreated = MutableStateFlow(false)

    fun createUser(request: CreateUserRequest) {
        viewModelScope.launch {
            _state.value = SignInState.Loading
            userRepository.createUser(request = request, action = this@UserViewModel)
        }
    }

    fun logIn(request: LogInRequest) {
        viewModelScope.launch {
            _state.value = SignInState.Loading
            userRepository.logIn(request = request, action = this@UserViewModel)
        }
    }
    fun profile(token: String) {
        viewModelScope.launch {
            _state.value = SignInState.Loading
            userRepository.profile(token = token, action = this@UserViewModel)
        }
    }

    fun restoreState() {
        _state.value = SignInState.FirstState
    }

    fun encodeImage(image: ImageBitmap): String? {
        return base64Converter.encodeImageToBase64(image)
    }

    fun decodeImage(image: String): Any? {
        return base64Converter.decodeBase64ToImage(image)
    }

    override fun onSuccess(user: CreateUserResponse) {
        _state.value = SignInState.Success(user)
    }

    override fun onError(error: String) {
        _state.value = SignInState.Error(error)
    }
}