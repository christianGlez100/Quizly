package com.sesi.quizly.data.repository

import com.sesi.quizly.data.entity.UserOauthProvidersTable
import com.sesi.quizly.model.UserOauth

interface UserOauthRepository {
    suspend fun addUserOauth(userOauth: UserOauth): UserOauth
}