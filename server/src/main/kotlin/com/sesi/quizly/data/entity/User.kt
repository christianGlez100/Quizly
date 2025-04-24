package com.sesi.quizly.data.entity

import com.sesi.quizly.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

@Serializable
data class User(
    val id: Long,
    val userName: String,
    val email: String,
    val password: String,
    val userImage: String,
    val userBio: String,
    val isCreator: Boolean,
    //@Serializable(with = LocalDateTimeSerializer::class)
    //val createdAt: LocalDateTime? = LocalDateTime.now(),
)

object Users : Table("users") {
    val id = long("user_id").autoIncrement()
    val userName = varchar("user_name", 100)
    val email = varchar("email", 150)
    val password = text("password")
    val userImage = text("user_image")
    val userBio = text("user_bio")
    val isCreator = bool("is_creator")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
