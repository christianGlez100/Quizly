package com.sesi.quizly.domain.model

data class QuestionModel(
    var id: Int,
    var question: String = "",
    var answer1: String = "",
    var answer1Points: String = "",
    var answer2: String = "",
    var answer2Points: String = "",
    var answer3: String = "",
    var answer3Points: String = "",
    var answer4: String = "",
    var answer4Points: String = "",
    var isComplete: Boolean = false
)