package com.sesi.quizly.data.repository

import com.sesi.quizly.model.User
import com.sesi.quizly.model.response.CreateUserResponse


interface UserRepository {
    suspend fun addUser(user: User): User
    suspend fun getProfile(userId: Long): User?
    suspend fun getUserById(id: Long): User
    suspend fun getUserByToken(token: String): User?
    suspend fun rollBack(id: Long):Int
    suspend fun login(email: String, password: String): CreateUserResponse?
}