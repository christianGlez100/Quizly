package com.sesi.quizly.plugin

import com.sesi.quizly.Greeting
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/signin") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}