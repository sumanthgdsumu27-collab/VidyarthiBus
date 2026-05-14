package com.example.vidyarthibus

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VidyarthiBusTheme {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ProfileScreen() {

    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {

        val uid = user?.uid ?: ""

        FirebaseDatabase.getInstance()
            .getReference("users")
            .child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    name = snapshot.child("name").value.toString()
                    email = snapshot.child("email").value.toString()
                    role = snapshot.child("role").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Profile",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Name: $name",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Email: $email",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Role: $role",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                auth.signOut()

                val intent = Intent(
                    context,
                    LoginActivity::class.java
                )

                context.startActivity(intent)

            }
        ) {

            Text("Logout")
        }
    }
}