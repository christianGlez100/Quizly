package com.sesi.quizly.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import com.sesi.quizly.ui.components.NavigationItem

object Graph {
    const val NAVIGATION_BAR_SCREEN_GRAPH = "navigationBarScreenGraph"
}

sealed class Routes(var route: String) {
    data object Home : Routes("home")
    data object SignIn : Routes("SignIn")
    data object HomeDetail : Routes("homeDetail")
    data object SettingDetail : Routes("settingDetail")
}

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        title = "Home",
        route = Routes.Home.route,
    ),
    NavigationItem(
        unSelectedIcon =  Icons.Outlined.Person,
        selectedIcon =  Icons.Filled.Person,
        title = "SignIn",
        route = Routes.SignIn.route,
    ),
)