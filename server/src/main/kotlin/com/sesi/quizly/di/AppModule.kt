package com.sesi.quizly.di

import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.data.repository.impl.UserRepositoryImpl
import com.sesi.quizly.service.UserService
import com.sesi.quizly.service.impl.UserServiceImpl
import org.koin.dsl.module


fun appModule() = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<UserService> { UserServiceImpl(get())}
}