package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: Long? = null,
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var userImage: String = "",
    var userBio: String = "",
    var isCreator: Boolean = false,
)

