package com.sesi.quizly.data.repository

import com.sesi.quizly.data.entity.User

interface UserRepository {
    suspend fun addUser(user: User): User
    suspend fun getUserById(id: Long): User
}