package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class Answer(
    val id: Long,
    val questionId: Long,
    val answerText: String,
    val imageUrl: String,
    val valueScore: Int
)

object Answers : Table("answers") {
    val id = long("id").autoIncrement()
    val questionId = long("question_id").references(Questions.id, ReferenceOption.CASCADE)
    val answerText = text("answer_text")
    val imageUrl = text("image_url")
    val valueScore = integer("value_score")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
