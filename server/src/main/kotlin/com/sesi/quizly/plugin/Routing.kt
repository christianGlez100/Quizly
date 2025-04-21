package com.sesi.quizly.plugin

import com.sesi.quizly.routes.userRoute
import com.sesi.quizly.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.koin.ktor.ext.get

fun Application.configureRouting(userService: UserService = get()) {
    routing {
        userRoute(userService)
    }
}