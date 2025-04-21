package com.sesi.quizly.service

import com.sesi.quizly.data.entity.User


interface UserService {
    suspend fun createUser(user: User)
}