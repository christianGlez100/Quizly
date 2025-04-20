package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val username: String,
    val password: String,
    val email: String
)

