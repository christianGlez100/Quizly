package com.sesi.quizly

class BuildConfigJs: BuildConfigHelper {
    override val baseUrl: String
        get() = ""
}

actual fun getBaseUrl(): BuildConfigHelper = BuildConfigJs()