package com.sesi.quizly.service

import com.sesi.quizly.model.User


interface TokenService {
    fun generateToken(userId: Long, userName: String): String
    fun validateToken(token: String): Boolean
    fun generateRefreshToken(): String
}