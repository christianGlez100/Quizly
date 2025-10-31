package com.sesi.quizly.service

import com.sesi.quizly.data.repository.UserRepository
import com.sesi.quizly.di.appModule
import com.sesi.quizly.model.User
import com.sesi.quizly.model.UserOauth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.assertNull
import org.koin.core.component.inject
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class UserServiceTest: KoinTest {


    // 1. Specify the Mock Provider (using Mockito)
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mock(clazz.java)
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(appModule())
    }
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    val userService:UserService by inject()

    @AfterTest
    fun tearDown(){
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun createUser_ok() = runTest {
        val userRepoMock = declareMock<UserRepository>()
        val userOauthMock = declareMock<UserOauthService>()

        val user = User()
        user.id = 1
        user.userName = "TestName"
        val userOauth = UserOauth(
            userId = 1,
            accessToken = "token",
            refreshToken = "refresh",
            provider = ""
        )

        whenever(userRepoMock.addUser(user)).thenReturn(user)
        whenever(userOauthMock.createUserToken(user)).thenReturn(userOauth)

        val response = userService.createUser(user)
        assertNotNull(response)
    }

    @Test
    fun create_user_fail() = runTest {
        val userRepoMock = declareMock<UserRepository>()

        val user = User()
        user.id = 1
        user.userName = "TestName"
        val userOauth = UserOauth(
            userId = 1,
            accessToken = "token",
            refreshToken = "refresh",
            provider = ""
        )

        whenever(userRepoMock.addUser(user)).thenReturn(null)
        val response = userService.createUser(user)
        assertNull(response)
    }

    @Test
    fun create_user_token_fail() = runTest {
        val userRepoMock = declareMock<UserRepository>()
        val userOauthMock = declareMock<UserOauthService>()

        val user = User()
        user.id = 1
        user.userName = "TestName"
        val userOauth = UserOauth(
            userId = 1,
            accessToken = "token",
            refreshToken = "refresh",
            provider = ""
        )

        whenever(userRepoMock.addUser(user)).thenReturn(user)
        whenever(userOauthMock.createUserToken(user)).thenReturn(null)

        val response = userService.createUser(user)
        assertNull(response)
    }

}