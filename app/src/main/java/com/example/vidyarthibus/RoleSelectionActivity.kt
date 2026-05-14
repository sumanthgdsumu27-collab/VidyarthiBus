package com.example.vidyarthibus

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme

class RoleSelectionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 HANDLE BACK BUTTON
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val intent = Intent(
                    this@RoleSelectionActivity,
                    LoginActivity::class.java
                )

                startActivity(intent)
                finish()
            }
        })

        setContent {
            VidyarthiBusTheme {
                RoleScreen()
            }
        }
    }
}

@Composable
fun RoleScreen() {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // ✅ PROFILE ICON TOP RIGHT
        IconButton(
            onClick = {

                val intent = Intent(
                    context,
                    ProfileActivity::class.java
                )

                context.startActivity(intent)

            },

            modifier = Modifier.align(Alignment.TopEnd)

        ) {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color.Black
            )
        }

        // ✅ MAIN CONTENT
        Column(
            modifier = Modifier.fillMaxSize(),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Select Your Role",
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {

                    val intent = Intent(
                        context,
                        ReportBusListActivity::class.java
                    )

                    context.startActivity(intent)

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            ) {

                Text("On Bus (Report)")
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {

                    val intent = Intent(
                        context,
                        ViewBusListActivity::class.java
                    )

                    context.startActivity(intent)

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            ) {

                Text("Waiting (View)")
            }
        }
    }
}