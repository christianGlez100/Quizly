package com.sesi.quizly.di

import com.sesi.quizly.data.datasource.UserDataSource
import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.ui.signin.viewmodel.SignInViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun appModule(engine: HttpClientEngine) = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client Log: $message")
                    }
                }
                level = io.ktor.client.plugins.logging.LogLevel.ALL
            }
        }
    }
    single { UserDataSource(get()) }.withOptions { createdAtStart() }
    single { UserRepository(get()) }.withOptions { createdAtStart() }
    viewModelOf(::SignInViewModel)
}