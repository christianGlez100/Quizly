package com.sesi.quizly.data.repository.impl

import com.sesi.quizly.data.entity.Answers
import com.sesi.quizly.data.entity.Questions
import com.sesi.quizly.data.entity.Quizzes
import com.sesi.quizly.data.repository.QuizRepository
import com.sesi.quizly.model.Answer
import com.sesi.quizly.model.Question
import com.sesi.quizly.model.Quiz
import com.sesi.quizly.plugin.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert

class QuizRepositoryImpl: QuizRepository {

    override suspend fun createQuiz(userId: Long, quiz: Quiz): Quiz = dbQuery{
        val insertQuiz = Quizzes.insert {
            it[this.userId] = userId
            it[title] = quiz.title
            it[description] = quiz.description
            it[isPublic] = quiz.isPublic
            it[isPremium] = quiz.isPremium
            it[price] = quiz.price
            it[coverImage] = quiz.coverImage
        }
        insertQuiz.resultedValues?.singleOrNull()?.let(::resultRowToQuiz) ?: error("No fue posible crear el quiz")
    }

    override suspend fun getQuizById(id: Long): List<Question> {
        val question = dbQuery {
            Questions.select(
                listOf(
                    Questions.id,
                    Questions.questionText
                )
            ).where {
                Questions.quizId eq id
            }.map { questionRow ->
                val answer = dbQuery {
                    Answers.select(
                        listOf(
                            Answers.id,
                            Answers.answerText,
                            Answers.imageUrl,
                            Answers.valueScore
                        )
                    ).where {
                        Answers.questionId eq questionRow[Questions.id]
                    }.map { answerRow ->
                        resultRowToAnswer(answerRow)
                    }
                }
                resultRowToQuestionAnswers(questionRow, answer)
            }
        }
        return question
    }


    override suspend fun getQuizzesByUserId(userId: Long): List<Quiz> {
        val quizzes = dbQuery {
            Quizzes.select(
                listOf(
                    Quizzes.id,
                    Quizzes.userId,
                    Quizzes.title,
                    Quizzes.description,
                    Quizzes.isPublic,
                    Quizzes.isPremium,
                    Quizzes.price,
                    Quizzes.coverImage,
                )
            ).where{
                Quizzes.userId eq userId
            }.map {
                resultRowToQuiz(it)
            }
        }
        return quizzes
    }

    override suspend fun deleteQuiz(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun rollBack(id: Long): Int = dbQuery {
        Quizzes.deleteWhere { Quizzes.id eq id }
    }

    override suspend fun addQuestion(idQuiz: Long, questions: List<Question>) = dbQuery {
        questions.forEach { question ->
            val insertQuestion = Questions.insert {
                it[quizId] = idQuiz
                it[questionText] = question.questionText
            }
            val questionRes = insertQuestion.resultedValues?.singleOrNull()?.let(::resultRowToQuestion)
            question.answers.forEach { answer ->
                val insertAnswer = Answers.insert {
                    it[questionId] = questionRes?.id!!
                    it[answerText] = answer.answerText
                    it[imageUrl] = answer.imageUrl
                    it[valueScore] = answer.valueScore
                }
            }
        }
    }

    private fun resultRowsToQuizzes(rows: List<ResultRow>): List<Quiz> {
        return rows.map { resultRowToQuiz(it) }
    }

    private fun resultRowToQuiz(row: ResultRow): Quiz {
        return Quiz(
            id = row[Quizzes.id],
            userId = row[Quizzes.userId],
            title = row[Quizzes.title],
            description = row[Quizzes.description],
            isPublic = row[Quizzes.isPublic],
            isPremium = row[Quizzes.isPremium],
            price = row[Quizzes.price],
            coverImage = row[Quizzes.coverImage]
        )
    }

    private fun resultRowToQuestion(row: ResultRow): Question {
        return Question(
            id = row[Questions.id],
            questionText = row[Questions.questionText]
        )
    }

    private fun resultRowToAnswer(it: ResultRow): Answer {
        return Answer(
            id = it[Answers.id],
            answerText = it[Answers.answerText],
            imageUrl = it[Answers.imageUrl],
            valueScore = it[Answers.valueScore]
        )
    }
    private fun resultRowToQuestionAnswers(questionRow: ResultRow, answers: List<Answer>): Question {
        return Question(
            id = questionRow[Questions.id],
            questionText = questionRow[Questions.questionText],
            answers = answers
        )
    }
}