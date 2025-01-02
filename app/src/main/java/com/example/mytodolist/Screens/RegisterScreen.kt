package com.example.mytodolist.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mytodolist.NavigationItem
import com.example.mytodolist.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.mindrot.jbcrypt.BCrypt

private lateinit var auth: FirebaseAuth
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
fun hashPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
    val hashedPassword = hashPassword(password)
}


@Composable
fun RegisterScreen(navController: NavController) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    val context = LocalContext.current.applicationContext // Context for the application

    Box {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Adjust padding for consistency
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center everything vertically
    ) {
        Text(
            "Create your account to start managing your tasks with To-Do List!",
            color = Color(0xFFE8692f7),
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 10.dp) // Padding adjusted to match LoginScreen
        )

        OutlinedTextField(textStyle = TextStyle(
            fontFamily = FontFamily.Serif, fontStyle = FontStyle.Italic
        ),
            value = Email,
            onValueChange = { Email = it },
            label = { Text("Email") },
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* do something */ }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .onKeyEvent {
                    if (it.key == Key.Enter) {/* do something */
                        true
                    } else {
                        false
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = Color(0xFFE8692f7),
                unfocusedLeadingIconColor = Color(0xFFE8692f7),
                focusedLabelColor = Color(0xFFE8692f7),
                focusedContainerColor = Color(0xFFE8692f7),
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFFE8692f7),
                unfocusedIndicatorColor = Color(0xFFE8692f7)
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Localized descriqption"
                )
            })

        OutlinedTextField(textStyle = TextStyle(
            fontFamily = FontFamily.Serif, fontStyle = FontStyle.Italic,
        ),
            value = Password,
            onValueChange = { Password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* do something */ }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .onKeyEvent {
                    if (it.key == Key.Enter) {/* do something */
                        true
                    } else {
                        false
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = Color(0xFFE8692f7),
                unfocusedLeadingIconColor = Color(0xFFE8692f7),
                focusedLabelColor = Color(0xFFE8692f7),
                focusedContainerColor = Color(0xFFE8692f7),
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFFE8692f7),
                unfocusedIndicatorColor = Color(0xFFE8692f7)
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Localized description"
                )
            })

        Button(
            onClick = {
                if (Email.isEmpty() || Password.isEmpty()) { // Alanlardan biri boşsa uyarı göster
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else if (
                    !isValidEmail(Email)
                ) { // Email geçerli değilse uyarı göster
                    Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                } else if (Password.length < 6) { // Şifre 6 karakterden kısa ise uyarı göster
                    Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                }

                else { // Alanlar doldurulmuşsa hesap oluştur

                    auth.createUserWithEmailAndPassword(Email, Password)    // Firebase ile hesap oluştur

                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    context,
                                    "Account created successfully. Navigate to login. }",
                                    Toast.LENGTH_LONG
                                ).show()

                                navController.navigate(NavigationItem.LoginScreen.route) // Login'e yönlendir
                            } else {
                                Toast.makeText(context, "Account creation failed", Toast.LENGTH_SHORT).show()
                            }

                        }
                }
            },
        colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8692f7),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(0.8f) // Same width as LoginScreen button
                .padding(top = 16.dp) // Adjusted padding to match LoginScreen
        ) {
            // Row for Image and Text inside the Button
            Row(
                verticalAlignment = Alignment.CenterVertically, // Aligns image and text vertically
                horizontalArrangement = Arrangement.Center // Centers them horizontally
            ) {
                // Image (Icon)
                Image(
                    painter = painterResource(id = R.drawable.baseline_login_24),
                    contentDescription = "Localized description",
                    modifier = Modifier.size(24.dp) // Icon size
                )

                Spacer(modifier = Modifier.width(8.dp)) // Space between image and text

                // Text for the button
                Text("Create Account", fontSize = 20.sp)
            }
        }
    }
}