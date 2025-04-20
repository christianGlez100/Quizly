package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.Calendar

@Serializable
data class Quiz(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val isPremium: Boolean,
    val price: Double,
    val coverImage: String,
    val createdAt: Calendar
)

object Quizzes : Table("quizzes") {
    val id = long("id").autoIncrement()
    val userId = long("user_id").references(Users.id, ReferenceOption.CASCADE)
    val title = varchar("title", 200)
    val description = text("description")
    val isPublic = bool("is_public")
    val isPremium = bool("is_premium")
    val price = double("price")
    val coverImage = text("cover_image")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}