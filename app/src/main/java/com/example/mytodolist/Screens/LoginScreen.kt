package com.example.mytodolist.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mytodolist.NavigationItem
import com.example.mytodolist.R
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    val localContext = LocalContext.current.applicationContext
    val sharedPref = localContext.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    var isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
    val user = auth.currentUser

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        // Ortalamayı burada yapıyoruz
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(


            modifier = Modifier
                .fillMaxWidth(0.8f) // Genişlik ayarlandı
                .wrapContentHeight(), // İçeriği dikeyde sarmak için
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp) // Her öğe arasındaki boşluğu ayarlayın
        ) {
            Text("Welcome MyToDo App", color = Color(0xFFE8692f7), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))

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
                    if (Email.isEmpty() || Password.isEmpty()) {
                        Toast.makeText(
                            localContext,
                            "Please fill in all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        auth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Giriş başarılı, kullanıcının bilgilerini kaydet
                                    editor.putBoolean("isLoggedIn", true)
                                    editor.putString("userEmail", Email)
                                    editor.putString("userPassword", Password)
                                    editor.commit() // commit kullanabilirsiniz
                                    // Ana sayfaya yönlendir
                                    Toast.makeText(
                                        localContext,
                                        "Login successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(NavigationItem.FirstScreen.route) {

                                    }
                                } else {
                                    Toast.makeText(
                                        localContext,
                                        "Email or Password is incorrect",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Giriş başarısız, kullanıcıyı uyar

                                    }
                                }
                            }
                    },

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8692f7), contentColor = White
                ),
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier.fillMaxWidth() // Genişliği daraltabilirsiniz
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Resim ve metni dikeyde ortalar
                    horizontalArrangement = Arrangement.Center // Yatayda ortalar
                ) {
                    //image
                    Image(
                        painter = painterResource(id = R.drawable.baseline_login_24),
                        contentDescription = "Localized description",
                        modifier = Modifier.size(24.dp) // İkon boyutunu ayarlayabilirsiniz
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // İkon ile metin arasında boşluk
                    // Metin
                    Text("Login", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Buton ile diğer öğe arasında boşluk

            // Kayıt olma ekranına yönlendiren buton
            TextButton(onClick = {
                navController.navigate(NavigationItem.RegisterScreen.route)  // Kayıt ekranına yönlendir
            }) {
                Text(
                    "Don't have an account? Sign up now",
                    color = Color(0xFFE8692f7),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavController(LocalContext.current))
}