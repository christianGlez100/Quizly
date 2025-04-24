package com.sesi.quizly.data.repository.impl


import com.sesi.quizly.data.entity.UserOauthProvidersTable
import com.sesi.quizly.data.entity.Users
import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.model.User
import com.sesi.quizly.plugin.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert

class UserRepositoryImpl(): UserRepository {

    override suspend fun addUser(user: User): User  = dbQuery {
        val insertUser = Users.insert {
            it[email] = user.email
            it[password] = user.password
            it[userName] = user.userName
            it[userImage] = user.userImage
            it[userBio] = user.userBio
            it[isCreator] = user.isCreator
        }
        insertUser.resultedValues?.singleOrNull()?.let(::resultRowToUser) ?: error("No fue posible crear el usuario")
    }

    override suspend fun getUserById(id: Long): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByToken(token: String): User? {
        val user = dbQuery {
            (UserOauthProvidersTable innerJoin Users).select(Users.columns).where{
                UserOauthProvidersTable.accessToken eq token
                UserOauthProvidersTable.userId eq Users.id
            }.map {
                resultRowToUser(it)
            }.singleOrNull()
        }
        return user
    }

    override suspend fun rollBack(id: Long):Int = dbQuery {
        Users.deleteWhere { Users.id eq id }
    }

    private fun resultRowToUser(row: ResultRow): User {
        return User(
            id = row[Users.id],
            userName = row[Users.userName],
            email = row[Users.email],
            password = row[Users.password],
            userImage = row[Users.userImage],
            userBio = row[Users.userBio],
            isCreator = row[Users.isCreator],
            //createdAt = row[Users.createdAt]
        )
    }
}
