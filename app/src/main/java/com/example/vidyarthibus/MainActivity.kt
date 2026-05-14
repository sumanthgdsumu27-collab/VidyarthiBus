package com.example.vidyarthibus

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VidyarthiBusTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(onClick = {
                        startActivity(Intent(this@MainActivity, ReportBusListActivity::class.java))
                    }) {
                        Text("On Bus (Report)")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {
                        startActivity(Intent(this@MainActivity, ViewBusListActivity::class.java))
                    }) {
                        Text("Waiting (View)")
                    }
                }
            }
        }
    }
}