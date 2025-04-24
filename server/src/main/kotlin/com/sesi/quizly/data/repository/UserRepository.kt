package com.sesi.quizly.data.repository

import com.sesi.quizly.model.User


interface UserRepository {
    suspend fun addUser(user: User): User
    suspend fun getUserById(id: Long): User
    suspend fun getUserByToken(token: String): User?
    suspend fun rollBack(id: Long):Int
}