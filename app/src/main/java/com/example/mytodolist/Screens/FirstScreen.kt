package com.example.mytodolist.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mytodolist.NavigationItem
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController) {
    auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val localContext = LocalContext.current.applicationContext
    val sharedPref = localContext.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
    if (isLoggedIn) {
        Text(text = "Welcome ${user?.email}", fontSize = 20.sp, color = Color.Black)
    } else {
        Text(text = "Please log in to continue", fontSize = 20.sp, color = Color.Red)
    }
}
