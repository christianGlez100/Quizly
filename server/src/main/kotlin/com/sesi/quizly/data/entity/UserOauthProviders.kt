package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.Calendar

@Serializable
data class UserOauthProviders(
    val id: Long,
    val userId: Long,
    val provider: String,
    val providerUserId: String,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: Calendar,
)

object UserOauthProvidersTable : Table("user_oauth_providers") {
    val id = long("id").autoIncrement()
    val userId = long("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val provider = varchar("provider", 50)
    val providerUserId = varchar("provider_user_id", 255)
    val accessToken = varchar("access_token", 255)
    val refreshToken = varchar("refresh_token", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
