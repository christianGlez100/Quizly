package com.sesi.quizly.service.impl

import com.sesi.quizly.data.entity.User
import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.service.UserService

class UserServiceImpl(private val userRepository: UserRepository): UserService {
    override suspend fun createUser(user: User) {
        userRepository.addUser(user)
    }



}