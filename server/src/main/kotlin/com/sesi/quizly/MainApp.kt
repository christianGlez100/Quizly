package com.sesi.quizly

import com.sesi.quizly.plugin.auth
import com.sesi.quizly.plugin.configureDI
import com.sesi.quizly.plugin.configureDatabases
import com.sesi.quizly.plugin.configureMonitoring
import com.sesi.quizly.plugin.configureRouting
import com.sesi.quizly.plugin.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureDI()
    auth()
    configureDatabases()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}