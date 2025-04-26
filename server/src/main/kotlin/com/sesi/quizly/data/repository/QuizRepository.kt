package com.sesi.quizly.data.repository

import com.sesi.quizly.data.entity.Quizzes
import com.sesi.quizly.model.Question
import com.sesi.quizly.model.Quiz

interface QuizRepository {
    suspend fun createQuiz(userId: Long, quiz: Quiz): Quiz
    suspend fun getQuizById(id: Long): Quizzes?
    suspend fun deleteQuiz(id: Long): Boolean
    suspend fun rollBack(id: Long):Int
    suspend fun addQuestion(idQuiz: Long, questions: List<Question>)

}