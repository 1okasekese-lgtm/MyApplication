package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                SocialSparkScreen()
            }
        }
    }
}

@Composable
fun SocialSparkScreen() {
    var timeInput by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val validOptions = listOf(
        "Morning",
        "Mid-morning",
        "Afternoon",
        "Afternoon Snack Time",
        "Dinner",
        "After Dinner / Night"
    )

    val suggestions = remember(timeInput) {
        val typed = timeInput.trim()

        if (typed.isEmpty()) {
            emptyList()
        } else {
            validOptions.filter {
                it.startsWith(typed, ignoreCase = true)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Social Spark",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Make a small connection today.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Enter a time of day:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = timeInput,
                onValueChange = {
                    timeInput = it
                },
                label = {
                    Text("What's the time of day?")
                },
                placeholder = {
                    Text("e.g. Morning")
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (suggestions.isNotEmpty()) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        suggestions.forEach { suggestion ->
                            Text(
                                text = suggestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        timeInput = suggestion
                                    }
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    val input = timeInput.trim().lowercase()

                    if (input.isEmpty()) {
                        resultMessage =
                            "Not quite! Try one of these: ${validOptions.joinToString(", ")}."
                        isError = true
                    } else {
                        resultMessage = when (input) {
                            "morning" ->
                                "Send a \"Good morning\" text to a family member."

                            "mid-morning", "midmorning" ->
                                "Reach out to a colleague with a quick \"Thank you\"."

                            "afternoon" ->
                                "Share a funny meme or interesting link with a friend."

                            "afternoon snack time", "afternoonsnacktime" ->
                                "Send a quick \"thinking of you\" message."

                            "dinner" ->
                                "Call a friend or relative for a 5-minute catch-up."

                            "after dinner / night", "after dinner", "night" ->
                                "Leave a thoughtful comment on a friend's post."

                            else ->
                                "Not quite! Try one of these: ${validOptions.joinToString(", ")}."
                        }

                        isError = resultMessage.startsWith("Not quite")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Get My Spark")
            }

            Button(
                onClick = {
                    timeInput = ""
                    resultMessage = ""
                    isError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Reset")
            }

            if (resultMessage.isNotEmpty()) {
                Surface(
                    color = if (isError) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text(
                        text = resultMessage,
                        modifier = Modifier.padding(16.dp),
                        color = if (isError) {
                            MaterialTheme.colorScheme.onErrorContainer
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SocialSparkScreenPreview() {
    MyApplicationTheme {
        SocialSparkScreen()
    }
}