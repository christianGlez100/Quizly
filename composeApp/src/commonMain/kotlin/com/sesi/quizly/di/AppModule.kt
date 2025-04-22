package com.sesi.quizly.di

import com.sesi.quizly.data.datasource.UserDataSource
import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.ui.signin.viewmodel.SignInViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun appModule() = module {
    //single { Source }.withOptions { createdAtStart() }
    //single { Repo() } { RepoImpl(get()) }
    //factory { SignInViewModel() }
    single { HttpClient { install(ContentNegotiation) { json() } } }
    single { UserDataSource(get()) }.withOptions { createdAtStart() }
    single { UserRepository(get()) }.withOptions { createdAtStart() }
    viewModelOf(::SignInViewModel)
}