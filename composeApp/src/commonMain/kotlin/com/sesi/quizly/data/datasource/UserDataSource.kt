package com.sesi.quizly.data.datasource

import com.sesi.quizly.data.client.request.CreateUserRequest
import com.sesi.quizly.data.client.response.CreateUserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class UserDataSource(
    private val ktorClient: HttpClient
) {
    private val urlBase = "http://192.168.50.232:8080"
    //private val urlBase = "http://127.0.0.1:8080"

    suspend fun createUser(
        request: CreateUserRequest,
        handler: (error: String?, response: CreateUserResponse?) -> Unit
        ) {
        try {
            val clientResponse = ktorClient.post("${urlBase}/user") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (clientResponse.status == HttpStatusCode.Created) {
                handler(null, clientResponse.body())
            } else {
                handler(clientResponse.bodyAsText(), null)
            }
        } catch (e: Exception) {
            handler(e.message, null)
        }

    }
}