package com.sesi.quizly.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sesi.quizly.ui.HomeScreen
import com.sesi.quizly.ui.login.LogInScreen
import com.sesi.quizly.ui.profile.ProfileScreen
import com.sesi.quizly.ui.signin.SignInScreen
import shared.preference.PreferenceManager

@Composable
fun MainNavigation(rootNavController: NavHostController, preferenceManager: PreferenceManager, snackbarHostState: SnackbarHostState) {
    NavHost(navController = rootNavController, startDestination = Routes.Home.route) {
        composable(route = Routes.Home.route) {
            HomeScreen(snackbarHostState = snackbarHostState)
        }
        composable(route = Routes.SignIn.route) {
            SignInScreen(
                snackbarHostState = snackbarHostState,
                preferenceManager = preferenceManager,
                navController = rootNavController
            )
        }
        composable(route = Routes.LogIn.route) {
            LogInScreen(
                snackbarHostState = snackbarHostState,
                preferenceManager = preferenceManager,
                navController = rootNavController
            )
        }
        composable(route = Routes.Profile.route) {
            ProfileScreen()
        }

    }
}