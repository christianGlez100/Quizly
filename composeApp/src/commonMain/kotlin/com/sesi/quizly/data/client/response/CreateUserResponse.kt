package com.sesi.quizly.data.client.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(
    val id: Long,
    val userName: String,
    val email: String,
    val userImage: String?=null,
    val userBio: String?=null,
    val isCreator: Boolean?=null,
    val tokenData: UserToken?=null
)

@Serializable
data class UserToken(
    val accessToken: String,
    val refreshToken: String,
    val createdAt: String
)