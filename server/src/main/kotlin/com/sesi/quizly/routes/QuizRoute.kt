package com.sesi.quizly.routes

import com.sesi.quizly.model.Quiz
import com.sesi.quizly.service.QuizService
import com.sesi.quizly.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Routing.quizRoute(quizService: QuizService, userService: UserService) {
    route("/quiz") {
        get("/quizzes") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val pageSize = call.request.queryParameters["pageSize"]?.toInt() ?: 10
            try {
                val result = quizService.getAllQuizzes(page, pageSize)
                result.let {
                    call.respond(HttpStatusCode.OK, it)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.message.toString())
            }
        }

        authenticate("auth-bearer") {
            post {
                val quiz = call.receive<Quiz>()
                val token = call.request.headers["Authorization"]!!.substringAfter("Bearer ")
                try {
                    val user = userService.validateToken(token)
                    val result = quizService.createQuiz(user?.id!!, quiz)
                    result.let {
                        call.respond(HttpStatusCode.Created, it!!)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message.toString())
                }
            }

            get("/user/quizzes") {
                val token = call.request.headers["Authorization"]!!.substringAfter("Bearer ")
                try {
                    val user = userService.validateToken(token)
                    val result = quizService.getQuizzesByUserId(user?.id!!)
                    result.let {
                        call.respond(HttpStatusCode.OK, it)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message.toString())
                }
            }

            get("/{id}") {
                val id = call.parameters["id"]!!.toLong()
                try {
                    val result = quizService.getQuizById(id)
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