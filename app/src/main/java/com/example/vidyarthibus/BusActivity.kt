package com.example.vidyarthibus

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidyarthibus.ui.theme.VidyarthiBusTheme
import com.google.firebase.database.*

class BusActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val busId = intent.getStringExtra("BUS_ID") ?: "Bus"

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("bus/$busId")

        setContent {
            VidyarthiBusTheme {
                BusScreen(busId, ref)
            }
        }
    }
}

@Composable
fun BusScreen(busId: String, ref: DatabaseReference) {

    var status by remember { mutableStateOf("Loading...") }
    var lastUpdatedText by remember { mutableStateOf("") }
    var isExpired by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    val isStudent =
        activity.intent.getBooleanExtra("IS_STUDENT", false)

    // ✅ Bus Numbers
    val busNumbers = mapOf(
        "Mysore → Bangalore" to "KA-09-ML-5335",
        "Mysore → Hassan" to "KA-09-HX-4919",
        "Bangalore → Tumkur" to "KA-05-TK-8821",
        "Mandya → Mysore" to "KA-11-MY-6732",
        "Hassan → Mangalore" to "KA-12-MG-2104"
    )

    // ✅ Alternative Contacts
    val routeContacts = mapOf(

        "Mysore → Bangalore" to listOf(
            "Driver 1: 9876543210",
            "Driver 2: 9123456789"
        ),

        "Mysore → Hassan" to listOf(
            "Driver 1: 9012345678",
            "Driver 2: 9988776655"
        ),

        "Bangalore → Tumkur" to listOf(
            "Driver 1: 9876501234",
            "Driver 2: 9123409876"
        ),

        "Mandya → Mysore" to listOf(
            "Driver 1: 9012309876",
            "Driver 2: 9988701234"
        ),

        "Hassan → Mangalore" to listOf(
            "Driver 1: 9876112233",
            "Driver 2: 9123451111"
        )
    )

    val autoContacts = routeContacts[busId] ?: emptyList()

    // ✅ Firebase Listener
    LaunchedEffect(Unit) {

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val statusValue =
                    snapshot.child("status")
                        .getValue(String::class.java)

                val timeValue =
                    snapshot.child("timestamp")
                        .getValue(Long::class.java)

                status = statusValue ?: "No Status"

                if (timeValue != null) {

                    val minutes =
                        (System.currentTimeMillis() - timeValue) / 60000

                    if (minutes > 15) {

                        lastUpdatedText = "No recent update"
                        isExpired = true

                    } else {

                        lastUpdatedText = "Updated $minutes min ago"
                        isExpired = false
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.e(
                    "Firebase",
                    "Error",
                    error.toException()
                )
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F5E9),
                        Color(0xFFF8FFF8)
                    )
                )
            )
    ) {

        // ✅ Back Button
        IconButton(
            onClick = { activity.finish() },
            modifier = Modifier.padding(10.dp)
        ) {

            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ✅ Route + Bus Number
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = busId,
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Text(
                    text = busNumbers[busId] ?: "",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            val displayStatus =
                if (isExpired)
                    "No recent update"
                else
                    status

            val color =
                if (isExpired) {
                    Color.Gray
                } else {
                    when (status) {

                        "EMPTY" -> Color.Green

                        "FEW SEATS" -> Color(0xFFFF9800)

                        "FULL" -> Color.Red

                        else -> Color.Black
                    }
                }

            // ✅ Status
            Text(
                text = "Status: $displayStatus",
                fontSize = 20.sp,
                color = color
            )

            Spacer(modifier = Modifier.height(6.dp))

            // ✅ Last Updated
            if (!isExpired && lastUpdatedText.isNotEmpty()) {

                Text(
                    text = lastUpdatedText,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ✅ Progress
            val progress =
                if (isExpired) {
                    0f
                } else {
                    when (status) {

                        "EMPTY" -> 0.2f

                        "FEW SEATS" -> 0.6f

                        "FULL" -> 1f

                        else -> 0f
                    }
                }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),

                color = color
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Alternative Transport
            if (isExpired || status == "FULL") {

                Text(
                    text = "Alternative Transport (Shared Auto)",
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                autoContacts.forEach {

                    Text(
                        text = it,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Admin Buttons
            if (!isStudent) {

                val buttonModifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(55.dp)

                Button(
                    onClick = {

                        ref.setValue(
                            mapOf(
                                "status" to "EMPTY",
                                "timestamp" to System.currentTimeMillis()
                            )
                        )
                    },

                    modifier = buttonModifier,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1CC5B7)
                    )
                ) {

                    Text(
                        "Empty",
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {

                        ref.setValue(
                            mapOf(
                                "status" to "FEW SEATS",
                                "timestamp" to System.currentTimeMillis()
                            )
                        )
                    },

                    modifier = buttonModifier,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1CC5B7)
                    )
                ) {

                    Text(
                        "Few Seats",
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {

                        ref.setValue(
                            mapOf(
                                "status" to "FULL",
                                "timestamp" to System.currentTimeMillis()
                            )
                        )
                    },

                    modifier = buttonModifier,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1CC5B7)
                    )
                ) {

                    Text(
                        "Full",
                        color = Color.Black
                    )
                }
            }
        }
    }
}