package com.sesi.quizly.data.client.request

import kotlinx.serialization.Serializable

@Serializable
class LogInRequest (
    val email: String,
    val password: String
)