package com.sesi.quizly.plugin

import com.sesi.quizly.service.TokenService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.bearer
import org.koin.ktor.ext.get

fun Application.auth(tokenService: TokenService = get()) {
    install(Authentication){
        bearer("auth-bearer") {
            authenticate {
                tokenCredential ->
                val token:String = tokenCredential.token
                val isValid = tokenService.validateToken(token)
                isValid
                //UserIdPrincipal(user.userName)
            }
        }
    }
}