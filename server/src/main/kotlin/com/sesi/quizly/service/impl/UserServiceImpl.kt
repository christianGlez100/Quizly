package com.sesi.quizly.service.impl


import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.model.User
import com.sesi.quizly.model.response.CreateUserResponse
import com.sesi.quizly.model.response.UserToken
import com.sesi.quizly.service.UserOauthService
import com.sesi.quizly.service.UserService

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userOauthService: UserOauthService
) : UserService {
    override suspend fun createUser(user: User): CreateUserResponse? {
        var userIdResult:Long? = null
        try {
            val userResult = userRepository.addUser(user)
            userIdResult = userResult.id
            val userOauth = userOauthService.createUserToken(userResult)
            val userResponse = CreateUserResponse(
                id = userResult.id!!,
                userName = userResult.userName,
                email = userResult.email,
                userImage = userResult.userImage,
                userBio = userResult.userBio,
                isCreator = userResult.isCreator,
                tokenData = UserToken(
                    accessToken = userOauth.accessToken,
                    refreshToken = userOauth.refreshToken,
                    createdAt = userOauth.createdAt
                )
            )
            return userResponse
        } catch (e:Exception){
            if (userIdResult != null) {
                userRepository.rollBack(userIdResult)
            }
            return null
        }
    }

    override suspend fun validateToken(token: String): User? {
        return userRepository.getUserByToken(token)
    }

    override suspend fun rollBack(id: Long) {
        userRepository.rollBack(id)
    }

    override suspend fun login(email: String, password: String): CreateUserResponse? {
        return userRepository.login(email, password)
    }


}