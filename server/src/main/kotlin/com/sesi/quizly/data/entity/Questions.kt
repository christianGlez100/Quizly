package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class Question(
    val id: Long,
    val quizId: Long,
    val questionText: String
)

object Questions : Table("questions") {
    val id = long("id").autoIncrement()
    val quizId = long("quiz_id").references(Quizzes.id, onDelete = ReferenceOption.CASCADE)
    val questionText = text("question_text")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}