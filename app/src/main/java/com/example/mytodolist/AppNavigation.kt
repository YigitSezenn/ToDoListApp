package com.example.mytodolist

enum class AppNavigation {
    LoginScreen,
    FirstScreen,
    RegisterScreen,
}
sealed class NavigationItem(val route: String) {
    object LoginScreen: NavigationItem(AppNavigation.LoginScreen.name)
    object FirstScreen: NavigationItem(AppNavigation.FirstScreen.name)
    object RegisterScreen: NavigationItem(AppNavigation.RegisterScreen.name)
}
