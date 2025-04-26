package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    val id: Long?=null,
    val userId: Long?=null,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val isPremium: Boolean,
    val price: Double,
    val coverImage: String,
    val questions: List<Question> = emptyList<Question>()
)