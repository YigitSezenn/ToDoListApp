package com.example.mytodolist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mytodolist.ui.theme.MyToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyToDoListTheme {
                val navController = rememberNavController()
                val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
                val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFEEEEEE) // İstediğiniz bir arka plan rengi
                ) { innerPadding ->
                    AppNavHost(
                        startDestination = if (isLoggedIn) {
                            NavigationItem.FirstScreen.route
                        } else {
                            NavigationItem.LoginScreen.route
                        }, //hatalı
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }
        }
    }
}
