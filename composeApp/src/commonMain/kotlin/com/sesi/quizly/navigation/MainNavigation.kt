package com.sesi.quizly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sesi.quizly.ui.HomeScreen
import com.sesi.quizly.ui.signin.SignInScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNavigation(rootNavController: NavHostController) {
    NavHost(navController = rootNavController, startDestination = Routes.Home.route) {
        composable(route = Routes.Home.route) {
            HomeScreen()
        }
        composable(route = Routes.SignIn.route) {
            SignInScreen()
        }
    }
}