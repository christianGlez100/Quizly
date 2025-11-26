package com.sesi.quizly.data.client.request

import kotlinx.serialization.Serializable

@Serializable
class CreateUserRequest(
    val id: Long,
    val userName: String,
    val email: String,
    val password: String,
    val userImage: ByteArray?,
    var userImageName: String = "",
    val userBio: String,
    val isCreator: Boolean
)
