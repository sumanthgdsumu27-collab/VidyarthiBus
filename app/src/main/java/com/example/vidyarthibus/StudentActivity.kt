package com.example.vidyarthibus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme
import com.google.firebase.database.*

class StudentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val busId = intent.getStringExtra("BUS_ID") ?: "Bus"

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("bus/$busId")

        setContent {
            VidyarthiBusTheme {
                studentScreen(busId, ref)
            }
        }
    }
}

@Composable
fun studentScreen(busId: String, ref: DatabaseReference) {

    var status by remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)
                status = value ?: "No Status"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error", error.toException())
            }
        })
    }

    val color = when (status) {
        "EMPTY" -> Color.Green
        "FEW SEATS" -> Color(0xFFFFA500)
        "FULL" -> Color.Red
        else -> Color.Black
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = busId, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Status: $status",
            fontSize = 20.sp,
            color = color
        )
    }
}