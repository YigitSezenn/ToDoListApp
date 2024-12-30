package com.example.mytodolist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mytodolist.Screens.FirstScreen
import com.example.mytodolist.Screens.LoginScreen
import com.example.mytodolist.Screens.RegisterScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.LoginScreen.route
) {
    NavHost (
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = NavigationItem.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = NavigationItem.FirstScreen.route) {
            FirstScreen(navController)
        }
        composable(route = NavigationItem.RegisterScreen.route) {
            RegisterScreen(navController)
        }
    }

}