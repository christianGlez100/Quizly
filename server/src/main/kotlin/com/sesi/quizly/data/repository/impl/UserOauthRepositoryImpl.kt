package com.sesi.quizly.data.repository.impl

import com.sesi.quizly.data.entity.UserOauthProvidersTable
import com.sesi.quizly.data.repository.UserOauthRepository
import com.sesi.quizly.model.UserOauth
import com.sesi.quizly.plugin.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

class UserOauthRepositoryImpl(): UserOauthRepository {
    override suspend fun addUserOauth(userOauth: UserOauth): UserOauth = dbQuery {
        val insertUser = UserOauthProvidersTable.insert {
            it[userId] = userOauth.userId
            it[provider] = userOauth.provider
            it[providerUserId] = userOauth.providerUserId
            it[accessToken] = userOauth.accessToken
            it[refreshToken] = userOauth.refreshToken
        }
        insertUser.resultedValues?.singleOrNull()?.let(::resultRowToUserToken) ?: error("No fue posible crear el token")
    }

    private fun resultRowToUserToken(row: ResultRow): UserOauth {
        return UserOauth(
            id = row[UserOauthProvidersTable.id],
            userId = row[UserOauthProvidersTable.userId],
            provider = row[UserOauthProvidersTable.provider],
            providerUserId = row[UserOauthProvidersTable.providerUserId],
            accessToken = row[UserOauthProvidersTable.accessToken],
            refreshToken = row[UserOauthProvidersTable.refreshToken],
            //createdAt = row[UserOauthProvidersTable.createdAt].toString()
        )
    }
}