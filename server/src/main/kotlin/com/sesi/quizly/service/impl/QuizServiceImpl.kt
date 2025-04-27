package com.sesi.quizly.service.impl

import com.sesi.quizly.data.repository.QuizRepository
import com.sesi.quizly.model.Question
import com.sesi.quizly.model.Quiz
import com.sesi.quizly.model.QuizPagination
import com.sesi.quizly.service.QuizService

class QuizServiceImpl(private val quizRepository: QuizRepository) : QuizService {

    override suspend fun createQuiz(userId: Long, quiz: Quiz): Quiz? {
        var quizIdResult: Long? = null
        try {
            val quizResult = quizRepository.createQuiz(userId, quiz)
            quizIdResult = quizResult.id
            addQuestion(quizIdResult!!, quiz.questions)
            return quizResult
        } catch (e: Exception) {
            if (quizIdResult != null) {
                quizRepository.rollBack(quizIdResult)
            }
            return null
        }

    }

    override suspend fun getAllQuizzes(page: Int, pageSize: Int): QuizPagination {
        val result: QuizPagination?
        try {
            result = quizRepository.getAllQuizzes(page, pageSize)
        } catch (e: Exception) {
            throw e
        }
        return result
    }

    override suspend fun getQuizById(id: Long): List<Question>? {
        var result:List<Question>? = null
        try {
            result = quizRepository.getQuizById(id)
        } catch (e: Exception) {
            throw e
        }
        return result
    }

    override suspend fun deleteQuiz(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addQuestion(idQuiz: Long, questions: List<Question>) {
        try {
            quizRepository.addQuestion(idQuiz, questions)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getQuizzesByUserId(userId: Long): List<Quiz> {
        var result:List<Quiz> = emptyList()
        try {
            result = quizRepository.getQuizzesByUserId(userId)
        } catch (e: Exception) {
            throw e
        }
        return result
    }

}