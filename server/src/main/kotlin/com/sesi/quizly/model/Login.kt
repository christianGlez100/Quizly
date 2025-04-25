package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
class Login (
    val email: String,
    val password: String
)