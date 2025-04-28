package com.sesi.quizly.service

import com.sesi.quizly.model.User
import com.sesi.quizly.model.UserOauth

interface UserOauthService {
    suspend fun createUserToken(user: User): UserOauth
    suspend fun updateUserToken(userId: Long, accessToken: String, refreshToken: String)
}