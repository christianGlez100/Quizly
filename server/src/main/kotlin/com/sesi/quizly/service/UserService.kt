package com.sesi.quizly.service

import com.sesi.quizly.model.User
import com.sesi.quizly.model.response.CreateUserResponse


interface UserService {
    suspend fun createUser(user: User): CreateUserResponse?
    suspend fun validateToken(token: String): User?
    suspend fun rollBack(id: Long)
    suspend fun login(email: String, password: String): CreateUserResponse?
}