package com.sesi.quizly.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sesi.quizly.navigation.MainNavigation
import com.sesi.quizly.navigation.Routes
import com.sesi.quizly.navigation.navigationItemsLists
import com.sesi.quizly.ui.components.BottomNavigationBar
import com.sesi.quizly.ui.components.NavigationItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shared.preference.PreferenceManager

class MainScreenModel : KoinComponent {
    val preferences: PreferenceManager by inject()
}
@Composable
fun MainScreen() {
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }
    val navigationItem by remember {
        derivedStateOf {
            navigationItemsLists.find { it.route == currentRoute }
        }
    }

    val isBottomBarVisible by remember {
        derivedStateOf {
            navigationItem?.route != Routes.HomeDetail.route
        }
    }
    val title by remember {
        derivedStateOf {
            navigationItem?.title ?: ""
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    val mainScreenModel: MainScreenModel = remember { MainScreenModel() }
    val preferenceManager = mainScreenModel.preferences

    Row {
        AnimatedVisibility(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            visible = isBottomBarVisible,
            enter = slideInHorizontally(
                // Slide in from the left
                initialOffsetX = { fullWidth -> -fullWidth }
            ),
            exit = slideOutHorizontally(
                // Slide out to the right
                targetOffsetX = { fullWidth -> -fullWidth }
            )
        ) {

        }
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isBottomBarVisible,
                    enter = slideInVertically(
                        // Slide in from the bottom
                        initialOffsetY = { fullHeight -> fullHeight }
                    ),
                    exit = slideOutVertically(
                        // Slide out to the bottom
                        targetOffsetY = { fullHeight -> fullHeight }
                    )
                ) {
                    BottomNavigationBar(
                        items = navigationItemsLists,
                        currentRoute = currentRoute,
                        onItemClick = { currentNavigationItem ->
                            onItemClick(rootNavController, currentNavigationItem)
                        }
                    )
                }
            },
            topBar = { QuizlyHeader(title = title, isBackVisible = false) {} },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){
                MainNavigation(
                    rootNavController = rootNavController,
                    preferenceManager = preferenceManager,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

private fun onItemClick(rootNavController: NavHostController, currentNavigationItem: NavigationItem) {
    rootNavController.navigate(currentNavigationItem.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        rootNavController.graph.startDestinationRoute?.let { startDestinationRoute ->
            // Pop up to the start destination, clearing the back stack
            popUpTo(startDestinationRoute) {
                // Save the state of popped destinations
                saveState = true
            }
        }

        // Configure navigation to avoid multiple instances of the same destination
        launchSingleTop = true

        // Restore state when re-selecting a previously selected item
        restoreState = true
    }
}