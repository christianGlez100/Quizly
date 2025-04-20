package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.Calendar

@Serializable
data class QuizView(
    val id: Long,
    val quizId: Long,
    val userId: Long,
    val viewedAt: Calendar
)

object QuizViews : Table("quiz_views") {
    val id = long("id").autoIncrement()
    val quizId = long("quiz_id").references(Quizzes.id, onDelete = ReferenceOption.CASCADE)
    val userId = long("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val viewedAt = datetime("viewed_at").defaultExpression(CurrentDateTime)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
