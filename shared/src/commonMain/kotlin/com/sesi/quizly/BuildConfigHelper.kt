package com.sesi.quizly

interface BuildConfigHelper {
    val baseUrl: String
}

expect fun getBaseUrl(): BuildConfigHelper