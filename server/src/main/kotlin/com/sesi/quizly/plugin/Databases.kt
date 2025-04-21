package com.sesi.quizly.plugin

import com.sesi.quizly.data.entity.Answers
import com.sesi.quizly.data.entity.Purchases
import com.sesi.quizly.data.entity.Questions
import com.sesi.quizly.data.entity.QuizViews
import com.sesi.quizly.data.entity.Quizzes
import com.sesi.quizly.data.entity.Results
import com.sesi.quizly.data.entity.UserOauthProvidersTable
import com.sesi.quizly.data.entity.UserQuizResults
import com.sesi.quizly.data.entity.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

private fun provideDataSource(
    url: String,
    driverClass: String,
    user: String,
    pass: String
): HikariDataSource {
    val hikariConfig = HikariConfig().apply {
        driverClassName = driverClass
        jdbcUrl = url
        username = user
        password = pass
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    return HikariDataSource(hikariConfig)
}

fun Application.configureDatabases() {
    val driverClass = environment.config.property("ktor.database.driverClassName").getString()
    val jdbcUrl = environment.config.property("ktor.database.jdbcURL").getString()
    val user = environment.config.property("ktor.database.user").getString()
    val pass = environment.config.property("ktor.database.password").getString()
    val db = Database.connect(provideDataSource(jdbcUrl, driverClass, user = "chris", pass = pass))
    transaction(db) {
        SchemaUtils.create(
            Users,
            Answers,
            Questions,
            Quizzes,
            Results,
            UserOauthProvidersTable,
            UserQuizResults,
            Purchases,
            QuizViews
        )
    }
}

suspend fun <T> dbQuery(block:suspend ()->T):T{
    return newSuspendedTransaction(Dispatchers.IO) { block() }
}