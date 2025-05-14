package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long? = null,
    val userName: String,
    val email: String,
    val password: String = "",
    val userImage: String,
    val userBio: String,
    val isCreator: Boolean,
)

