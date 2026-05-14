package com.example.vidyarthibus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VidyarthiBusTheme {
                RegisterScreen()
            }
        }
    }
}

@Composable
fun RegisterScreen() {

    // ✅ FIXED CONTEXT ERROR
    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Student") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Register",
            fontSize = 28.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        // NAME
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Name")
            },
            textStyle = TextStyle(
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            textStyle = TextStyle(
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // PASSWORD
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            textStyle = TextStyle(
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        // ROLE BUTTONS
        Row {

            Button(
                onClick = {
                    role = "Student"
                }
            ) {
                Text("Student")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    role = "Admin"
                }
            ) {
                Text("Admin")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // REGISTER BUTTON
        Button(
            onClick = {

                if (name.isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty()
                ) {

                    Toast.makeText(
                        context,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {

                                val uid = auth.currentUser?.uid ?: ""

                                val userData = mapOf(
                                    "name" to name,
                                    "email" to email,
                                    "role" to role
                                )

                                database.getReference("users")
                                    .child(uid)
                                    .setValue(userData)

                                Toast.makeText(
                                    context,
                                    "Registration Successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // GO TO LOGIN PAGE
                                val intent = Intent(
                                    context,
                                    LoginActivity::class.java
                                )

                                context.startActivity(intent)

                            } else {

                                Toast.makeText(
                                    context,
                                    it.exception?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        ) {

            Text("Register")
        }
    }
}