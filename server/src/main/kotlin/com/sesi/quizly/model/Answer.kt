package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val answerText: String,
    val imageUrl: String,
    val valueScore: Int
)
