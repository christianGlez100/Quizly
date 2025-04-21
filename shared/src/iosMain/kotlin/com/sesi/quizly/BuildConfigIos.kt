package com.sesi.quizly

class BuildConfigIos: BuildConfigHelper {
    override val baseUrl: String
        get() = ""
}

actual fun getBaseUrl(): BuildConfigHelper = BuildConfigIos()