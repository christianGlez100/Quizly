package com.sesi.quizly.data.datasource

import com.sesi.quizly.data.client.request.CreateUserRequest
import com.sesi.quizly.data.client.response.CreateUserResponse
import com.sesi.quizly.data.client.request.LogInRequest
import com.sesi.quizly.data.client.response.BaseResponse
import com.sesi.quizly.data.client.response.SuccessResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI

class UserDataSource(
    private val ktorClient: HttpClient
) {
    private val urlBase = "http://nasdb.chrisstek.icu"
    //private val urlBase = "http://192.168.50.232:8080"
    //private val urlBase = "http://127.0.0.1:8080"

    @OptIn(InternalAPI::class)
    suspend fun createUser(
        request: CreateUserRequest,
        handler: (error: String?, response: CreateUserResponse?) -> Unit
        ) {
        try {
            val multipart = MultiPartFormDataContent(formData{
                append("userName", request.userName)
                append("email", request.email)
                append("password", request.password)
                append("userBio", request.userBio)
                append("isCreator", request.isCreator)
                append(key = "userImage", value = request.userImage!!, headers = Headers.build {
                    //append(HttpHeaders.ContentType, ContentType.MultiPart.FormData) // Or the actual image type
                    append(HttpHeaders.ContentDisposition, "filename=\"${request.userImageName}\"")
                })
            })
            val clientResponse = ktorClient.post("${urlBase}/user") {
               // contentType(ContentType.Application.M)
                contentType(ContentType.MultiPart.FormData)
                setBody(multipart)
            }
            val resp = clientResponse.body<SuccessResponse<CreateUserResponse>>()
            if (clientResponse.status == HttpStatusCode.Created) {
                handler(null, resp.data)
            } else {
                handler("Ocurrio un error", null)
            }
        } catch (e: Exception) {
            handler(/*"Ocurrio un error"*/e.message, null)
        }

    }

    suspend fun logIn(
        request: LogInRequest,
        handler: (error: String?, response: CreateUserResponse?) -> Unit
    ) {
        try {
            val clientResponse = ktorClient.post("${urlBase}/user/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            val resp = clientResponse.body<SuccessResponse<CreateUserResponse>>()
            if (clientResponse.status == HttpStatusCode.OK && resp.status == "success") {
                handler(null, resp.data)
            } else {
                handler(resp.message, null)
            }
        } catch (e: Exception) {
            handler(e.message, null)
        }
    }

    suspend fun profile(
        token: String,
        handler: (error: String?, response: CreateUserResponse?) -> Unit
    ) {
        try {
            val profileResponse = ktorClient.get("${urlBase}/user/profile") {
                contentType(ContentType.Application.Json)
                bearerAuth(token)
            }
            val resp = profileResponse.body<SuccessResponse<CreateUserResponse>>()
            if (profileResponse.status == HttpStatusCode.OK && resp.status == "success") {
                handler(null, resp.data)
            } else {
                handler(resp.message, null)
            }
        } catch (e: Exception) {
            handler(e.message, null)
        }
    }
}