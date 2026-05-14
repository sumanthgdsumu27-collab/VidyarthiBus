package com.example.vidyarthibus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme

class ViewBusListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VidyarthiBusTheme {
                ViewBusScreen()
            }
        }
    }
}

@Composable
fun ViewBusScreen() {

    val context = LocalContext.current
    val activity = context as Activity

    val routes = listOf(
        "Mysore → Bangalore",
        "Mysore → Hassan",
        "Bangalore → Tumkur",
        "Mandya → Mysore",
        "Hassan → Mangalore"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)) // ✅ same background
    ) {

        // 🔙 Back button
        IconButton(
            onClick = { activity.finish() },
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Select Route (Student)",
                fontSize = 24.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            routes.forEach { route ->

                Button(
                    onClick = {
                        val intent = Intent(context, BusActivity::class.java)
                        intent.putExtra("BUS_ID", route)
                        intent.putExtra("IS_STUDENT", true)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1CC5B7)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(55.dp)
                ) {
                    Text(route, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}