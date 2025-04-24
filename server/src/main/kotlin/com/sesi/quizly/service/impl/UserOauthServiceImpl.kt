package com.sesi.quizly.service.impl

import com.sesi.quizly.data.repository.UserOauthRepository
import com.sesi.quizly.model.User
import com.sesi.quizly.model.UserOauth
import com.sesi.quizly.service.TokenService
import com.sesi.quizly.service.UserOauthService

class UserOauthServiceImpl(
    private val userOauthRepository: UserOauthRepository,
    private val tokenService: TokenService
) : UserOauthService {

    override suspend fun createUserToken(user: User): UserOauth {
        val accessToken = tokenService.generateToken(user)
        val refreshToken = tokenService.generateRefreshToken()
        val userOauth = UserOauth(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = user.id!!,
            provider = "local"
        )
        return userOauthRepository.addUserOauth(userOauth)
    }

}