package com.sesi.quizly.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizPagination(
    val quizzes: List<Quiz>,
    val currentPage: Int,
    val totalPages: Int,
    val totalQuizzes: Int
)
