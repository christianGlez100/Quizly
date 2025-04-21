package com.sesi.quizly.data.datasource

import androidx.compose.runtime.remember
import com.sesi.quizly.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class UserDataSource(
    private val ktorClient: HttpClient
) {
    private val urlBase = BuildConfig().getBaseUrl()
    suspend fun createUser() {
        val clientResponse = ktorClient.post("${urlBase}/users") {
        }
    }
}