package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Long? = null,
    val questionText: String,
    val answers: List<Answer> = emptyList<Answer>()
)
