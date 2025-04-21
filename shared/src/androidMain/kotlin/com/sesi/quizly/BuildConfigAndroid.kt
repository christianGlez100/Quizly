package com.sesi.quizly

import java.util.Properties

class BuildConfigAndroid() : BuildConfigHelper {
    val properties = Properties()
    override val baseUrl: String = properties.getProperty("BASE_DEV_URL") ?: ""

}

actual fun getBaseUrl(): BuildConfigHelper = BuildConfigAndroid()