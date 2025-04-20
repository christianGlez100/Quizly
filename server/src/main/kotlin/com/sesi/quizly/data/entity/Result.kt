package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class Result(
    val id: Long,
    val quizId: Long,
    val title: String,
    val description: String,
    val imageUrl: String
)

object Results : Table("results") {
    val id = long("id").autoIncrement()
    val quizId = long("quiz_id").references(Quizzes.id, ReferenceOption.CASCADE)
    val title = text("title")
    val description = text("description")
    val imageUrl = text("image_url")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
