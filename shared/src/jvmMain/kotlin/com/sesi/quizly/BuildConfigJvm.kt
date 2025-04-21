package com.sesi.quizly

class BuildConfigJvm: BuildConfigHelper {
    override val baseUrl: String
        get() = ""
}

actual fun getBaseUrl(): BuildConfigHelper = BuildConfigJvm()