package com.sesi.quizly.routes

import com.sesi.quizly.model.Login
import com.sesi.quizly.model.User
import com.sesi.quizly.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Routing.userRoute(userService: UserService) {
    route("/user") {
        post("/login") {
            val login = call.receive<Login>()
            try {
                val result = userService.login(login.email, login.password)
                result.let {
                    call.respond(HttpStatusCode.OK, it!!)
                }
            } catch (e:Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.message.toString())
            }
        }
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

        authenticate("auth-bearer") {
            get("/profile") {
                val token = call.request.headers["Authorization"]!!.substringAfter("Bearer ")
                try {
                    val user = userService.validateToken(token)
                    val result = userService.getProfile(user?.id!!)
                    result.let {
                        call.respond(HttpStatusCode.OK, it!!)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message.toString())
                }
            }
        }
    }
}