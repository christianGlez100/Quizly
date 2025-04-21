package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.Calendar

@Serializable
data class UserQuizResult(
    val id: Long,
    val quizId: Long,
    val userId: Long,
    val resultId: Long,
    val score: Int,
    val createdAt: String
)

object UserQuizResults : Table("user_quiz_results") {
    val id = long("id").autoIncrement()
    val quizId = long("quiz_id").references(Quizzes.id, onDelete = ReferenceOption.CASCADE)
    val userId = long("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val resultId = long("result_id").references(Results.id, onDelete = ReferenceOption.CASCADE)
    val score = integer("score")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}