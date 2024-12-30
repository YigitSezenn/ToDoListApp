package com.example.mytodolist.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.mytodolist.NavigationItem
@Composable
fun FirstScreen (navController: NavController){
    Box() {
        Text("First Screen")
    }
    // You can manage operations like going back with navController
    // navController.popBackStack() // Can be used to go back

}