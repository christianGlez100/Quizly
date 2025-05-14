package com.sesi.quizly.data.repository

import com.sesi.quizly.data.client.request.CreateUserRequest
import com.sesi.quizly.data.client.request.LogInRequest
import com.sesi.quizly.data.client.response.CreateUserResponse
import com.sesi.quizly.data.datasource.UserDataSource

class UserRepository(private val dataSource: UserDataSource) {

    suspend fun createUser(
        request: CreateUserRequest,
        action: UserAction
    ) {
        dataSource.createUser(request) { error, response ->
            if (error != null) {
                action.onError(error)
            }
            if (response != null) {
                action.onSuccess(response)
            }
        }
    }
    suspend fun logIn(
        request: LogInRequest,
        action: UserAction
    ) {
        dataSource.logIn(request) { error, response ->
            if (error != null) {
                action.onError(error)
            }
            if (response != null) {
                action.onSuccess(response)
            }
        }
    }
    suspend fun profile(
        token: String,
        action: UserAction
    ) {
        dataSource.profile(token) { error, response ->
            if (error != null) {
                action.onError(error)
            }
            if (response != null) {
                action.onSuccess(response)
            }
        }
    }
}

interface UserAction {
    fun onSuccess(user: CreateUserResponse)
    fun onError(error: String)
}
