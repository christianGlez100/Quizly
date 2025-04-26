package com.sesi.quizly.data.entity

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table


object Answers : Table("answers") {
    val id = long("id").autoIncrement()
    val questionId = long("question_id").references(Questions.id, ReferenceOption.CASCADE)
    val answerText = text("answer_text")
    val imageUrl = text("image_url")
    val valueScore = integer("value_score")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
