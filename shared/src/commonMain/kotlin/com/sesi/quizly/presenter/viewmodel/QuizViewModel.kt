package com.sesi.quizly.presenter.viewmodel

import androidx.lifecycle.ViewModel
import com.sesi.quizly.domain.model.QuestionModel


class QuizViewModel(): ViewModel() {

    fun isCompleteQuestion(question: QuestionModel): Boolean {
        return question.question.isNotBlank() && question.answer1.isNotBlank()
                && question.answer2.isNotBlank() && question.answer3.isNotBlank()
                && question.answer4.isNotBlank() && question.answer1Points.isNotBlank()
                && question.answer2Points.isNotBlank() && question.answer3Points.isNotBlank()
                && question.answer4Points.isNotBlank()
    }
}