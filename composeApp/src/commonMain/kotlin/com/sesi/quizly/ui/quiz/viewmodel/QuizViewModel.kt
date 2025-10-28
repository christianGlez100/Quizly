package com.sesi.quizly.ui.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.sesi.quizly.ui.quiz.Question


class QuizViewModel(): ViewModel() {

    fun isCompleteQuestion(question: Question): Boolean {
        return question.question.isNotBlank() && question.answer1.isNotBlank()
                && question.answer2.isNotBlank() && question.answer3.isNotBlank()
                && question.answer4.isNotBlank() && question.answer1Points.isNotBlank()
                && question.answer2Points.isNotBlank() && question.answer3Points.isNotBlank()
                && question.answer4Points.isNotBlank()
    }
}