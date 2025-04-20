package com.sesi.quizly

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform