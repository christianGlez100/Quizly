package com.sesi.quizly.data.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

@Serializable
data class Purchase(
    val id: Long,
    val userId: Long,
    val quizId: Long,
    val amountPaid: Double,
    val purchaseDate: String
)

object Purchases : Table("purchases") {
    val id = long("id").autoIncrement()
    val userId = long("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val quizId = long("quiz_id").references(Quizzes.id, onDelete = ReferenceOption.CASCADE)
    val amountPaid = double("amount_paid")
    val purchaseDate = datetime("purchase_date").defaultExpression(CurrentDateTime)
}