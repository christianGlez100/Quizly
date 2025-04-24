package com.sesi.quizly.model

class UserOauth(
    val id:Long = 0,
    val userId:Long,
    val provider:String,
    val providerUserId:String = "",
    val accessToken:String,
    val refreshToken:String,
    val createdAt:String = ""
)