package com.sesi.quizly.routes

import com.sesi.quizly.model.Login
import com.sesi.quizly.model.User
import com.sesi.quizly.model.response.ErrorResponse
import com.sesi.quizly.model.response.SuccessResponse
import com.sesi.quizly.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.jvm.javaio.copyTo
import java.io.File
import java.util.UUID.randomUUID


fun Routing.userRoute(userService: UserService) {
    route("/user") {
        post("/login") {
            val login = call.receive<Login>()
            try {
                val result = userService.login(login.email, login.password)
                result.let {
                    if (it!!.id == 0L) {
                        call.respond(status = HttpStatusCode.OK, ErrorResponse(message = "Error, valida tus credenciales", data = it, status = "error"))
                    } else {
                        call.respond(HttpStatusCode.OK, SuccessResponse(data = it, message = "Success", status = "success"))
                    }
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse(message = "Server error", data = null, status = "error"))
            }
        }

        post {
            val newUserMultipart = call.receiveMultipart()
            var imageUrl: String? = null
            val userData = User()
            newUserMultipart.forEachPart {  part ->
                when (part) {
                    is PartData.FormItem -> {
                        when (part.name) {
                            "userName" -> { userData.userName = part.value}
                            "email" -> {userData.email = part.value}
                            "password" -> {userData.password = part.value}
                            "userBio" -> {userData.userBio = part.value}
                            "isCreator" -> {userData.isCreator = part.value.toBoolean()}
                            else -> {println("Ignoring unknown form field: ${part.name}")}
                        }
                    }
                    is PartData.FileItem -> {
                        if (part.name == "userImage") {
                            val originalFileName = part.originalFileName ?: randomUUID().toString()
                            //val fileBytes = part.streamProvider().readBytes()

                            val uploadDir = File("uploads/user") // Store in a directory like 'uploads/items'
                            if (!uploadDir.exists()) {
                                uploadDir.mkdirs()
                            }
                            val uniqueFileName = "${randomUUID()}_${originalFileName}"
                            val savedFile = File(uploadDir, uniqueFileName)

                            try {
                                val channel: ByteReadChannel = part.provider()
                                savedFile.outputStream().use { outputStream ->
                                    channel.copyTo(outputStream)
                                }
                                imageUrl = savedFile.path
                                userData.userImage = imageUrl ?: ""
                                println("Saved image file: $imageUrl")
                               // imageUrl = "/uploads/items/$uniqueFileName" // Example relative path
                            } catch (e: Exception) {
                                e.printStackTrace()
                                call.respond(HttpStatusCode.InternalServerError, SuccessResponse(message = "Failed to save user image.", data = null, status = "error"))
                                part.dispose()
                                //return@post
                            }
                        } else {
                            println("Ignoring unexpected file part: ${part.name}")
                        }
                    }

                    is PartData.BinaryChannelItem -> {}
                    is PartData.BinaryItem -> {}
                }
                part.dispose()
            }
            try {
                val result = userService.createUser(userData)
                result.let {
                    call.respond(HttpStatusCode.Created, SuccessResponse(data = it!!, message = "Success", status = "success"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SuccessResponse( message = e.message.toString(), data = null, status = "error"))
            }
        }

        authenticate("auth-bearer") {
            get("/profile") {
                val token = call.request.headers["Authorization"]!!.substringAfter("Bearer ")
                try {
                    val user = userService.validateToken(token)
                    val result = userService.getProfile(user?.id!!)
                    result.let {
                        call.respond(HttpStatusCode.OK, SuccessResponse(data = it!!, message = "Success", status = "success"))
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, SuccessResponse( message = e.message.toString(), data = null, status = "error"))
                }
            }
        }
    }
}