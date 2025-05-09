package com.sesi.quizly.ui.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesi.quizly.data.client.request.CreateUserRequest
import com.sesi.quizly.data.client.response.CreateUserResponse
import com.sesi.quizly.data.repository.UserAction
import com.sesi.quizly.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SignInState {
    object Loading : SignInState()
    object FirstState : SignInState()
    data class Success(val user: CreateUserResponse) : SignInState()
    data class Error(val error: String) : SignInState()
}

class SignInViewModel(
    private val userRepository: UserRepository
) : ViewModel(), UserAction {

    private val _state = MutableStateFlow<SignInState>(SignInState.FirstState)
    val state: StateFlow<SignInState> = _state
    val userCreated = MutableStateFlow(false)

    fun createUser(request: CreateUserRequest) {
        viewModelScope.launch {
            _state.value = SignInState.Loading
            userRepository.createUser(request = request, action = this@SignInViewModel)
        }
    }

    fun restoreState() {
        _state.value = SignInState.FirstState
    }

    override fun isUserCreated(user: CreateUserResponse) {
        _state.value = SignInState.Success(user)
    }

    override fun onError(error: String) {
        _state.value = SignInState.Error(error)
    }
}