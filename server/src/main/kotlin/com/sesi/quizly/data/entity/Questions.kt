package com.sesi.quizly.data.entity

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Questions : Table("questions") {
    val id = long("id").autoIncrement()
    val quizId = long("quiz_id").references(Quizzes.id, onDelete = ReferenceOption.CASCADE)
    val questionText = text("question_text")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}