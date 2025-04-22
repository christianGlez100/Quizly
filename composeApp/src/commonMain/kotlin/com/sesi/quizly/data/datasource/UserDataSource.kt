package com.sesi.quizly.data.datasource

import com.sesi.quizly.BuildConfig
import com.sesi.quizly.data.client.request.CreateUserRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class UserDataSource(
    private val ktorClient: HttpClient
) {
    private val urlBase = BuildConfig().getBaseUrl()

    suspend fun createUser(
        request: CreateUserRequest,
        handler: (error: String?, response: Boolean) -> Unit
        ) {
        try {
            val clientResponse = ktorClient.post("${urlBase}/users") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (clientResponse.status == HttpStatusCode.Created) {
                handler(null, true)
            } else {
                handler(clientResponse.bodyAsText(), false)
            }
        } catch (e: Exception) {
            handler(e.message, false)
        }

    }
}