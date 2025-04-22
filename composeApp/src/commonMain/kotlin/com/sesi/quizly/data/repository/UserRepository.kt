package com.sesi.quizly.data.repository

import com.sesi.quizly.data.client.request.CreateUserRequest
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
            if (response) {
                action.isUserCreated(response)
            }
        }
    }
}

interface UserAction {
    fun isUserCreated(isCreated: Boolean)
    fun onError(error: String)
}