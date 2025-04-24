package com.sesi.quizly.routes

import com.sesi.quizly.model.User
import com.sesi.quizly.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.jetbrains.exposed.exceptions.ExposedSQLException


fun Routing.userRoute(userService: UserService) {
    route("/user") {
        post {
            val newUser = call.receive<User>()
            try {
                val result = userService.createUser(newUser)
                result.let {
                    call.respond(HttpStatusCode.Created, it!!)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.message.toString())
            }
        }
    }
}