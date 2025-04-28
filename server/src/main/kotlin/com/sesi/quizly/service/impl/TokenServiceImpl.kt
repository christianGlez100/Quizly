package com.sesi.quizly.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.sesi.quizly.service.TokenService
import java.util.Date
import java.util.UUID

class TokenServiceImpl(): TokenService {
    private val secret = "secret" // Cambia esto por una clave segura
    private val issuer = "issuer"
    private val audience = "audience"
    private val algorithm = Algorithm.HMAC256(secret)
    private val validityInMs = 36_000_00 * 10 // 10 hours

    private val jwtVerifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    override fun generateToken(userId: Long, userName: String): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withClaim("userName", userName)
            .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
            .sign(algorithm)
    }

    override fun validateToken(token: String): Boolean {
        return try {
            jwtVerifier.verify(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun generateRefreshToken(): String {
        return UUID.randomUUID().toString()
    }

}