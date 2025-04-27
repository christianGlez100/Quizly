package com.sesi.quizly.service

import com.sesi.quizly.model.Question
import com.sesi.quizly.model.Quiz
import com.sesi.quizly.model.QuizPagination

interface QuizService {
    suspend fun createQuiz(userId: Long, quiz: Quiz): Quiz?
    suspend fun getAllQuizzes(page: Int, pageSize: Int): QuizPagination
    suspend fun getQuizById(id: Long): List<Question>?
    suspend fun deleteQuiz(id: Long): Boolean
    suspend fun addQuestion(idQuiz: Long, questions: List<Question>)
    suspend fun getQuizzesByUserId(userId: Long): List<Quiz>

}