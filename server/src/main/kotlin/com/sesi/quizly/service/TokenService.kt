package com.sesi.quizly.service


interface TokenService {
    fun generateToken(userId: Long, userName: String): String
    fun validateToken(token: String): Boolean
    fun generateRefreshToken(): String
}