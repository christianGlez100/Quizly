package com.sesi.quizly.di

import com.sesi.quizly.data.repository.QuizRepository
import com.sesi.quizly.data.repository.UserOauthRepository
import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.data.repository.impl.QuizRepositoryImpl
import com.sesi.quizly.data.repository.impl.UserOauthRepositoryImpl
import com.sesi.quizly.data.repository.impl.UserRepositoryImpl
import com.sesi.quizly.service.QuizService
import com.sesi.quizly.service.TokenService
import com.sesi.quizly.service.UserOauthService
import com.sesi.quizly.service.UserService
import com.sesi.quizly.service.impl.QuizServiceImpl
import com.sesi.quizly.service.impl.TokenServiceImpl
import com.sesi.quizly.service.impl.UserOauthServiceImpl
import com.sesi.quizly.service.impl.UserServiceImpl
import org.koin.dsl.module


fun appModule() = module {
    single<QuizRepository> { QuizRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<UserOauthRepository> { UserOauthRepositoryImpl() }
    single<TokenService> { TokenServiceImpl() }
    single<UserOauthService> { UserOauthServiceImpl(get(), get()) }
    single<UserService> { UserServiceImpl(get(), get())}
    single<QuizService> { QuizServiceImpl(get()) }
}