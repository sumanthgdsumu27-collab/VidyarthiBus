package com.example.vidyarthibus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VidyarthiBusTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.bus_logo),
            contentDescription = "Bus Logo",
            modifier = Modifier.size(140.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Welcome to Vidyarthi Bus",
            fontSize = 24.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(25.dp))

        // EMAIL FIELD
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // PASSWORD FIELD
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(25.dp))

        // LOGIN BUTTON
        Button(
            onClick = {

                if (email.isEmpty() || password.isEmpty()) {

                    Toast.makeText(
                        context,
                        "Enter Email and Password",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    context,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(
                                    context,
                                    RoleSelectionActivity::class.java
                                )

                                context.startActivity(intent)

                            } else {

                                Toast.makeText(
                                    context,
                                    task.exception?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)

        ) {

            Text("Login")
        }

        Spacer(modifier = Modifier.height(15.dp))

        // REGISTER BUTTON
        TextButton(
            onClick = {

                val intent = Intent(
                    context,
                    RegisterActivity::class.java
                )

                context.startActivity(intent)
            }
        ) {

            Text(
                text = "New User? Register",
                color = Color.Blue
            )
        }
    }
}