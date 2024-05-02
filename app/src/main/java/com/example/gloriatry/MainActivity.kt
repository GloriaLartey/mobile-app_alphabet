package com.example.gloriatry

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), OnInitListener {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          //  Surface(color = MaterialTheme.colorScheme.background) {
               // AlphabetScreen(textToSpeech = remember { TextToSpeech(this@MainActivity, this) })
          //  }
            Surface(color = Color.Green) {
                AlphabetScreen(textToSpeech = remember { TextToSpeech(this@MainActivity, this) })
            }
            Text(
                text = "Learn Your Alphabets!!",
                color = Color.Black, // White text on black background
                fontSize = MaterialTheme.typography.displayLarge.fontSize, // Adjust font size as needed
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // Add padding
            )
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Text-to-Speech engine is ready for use
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown()
    }
}

@Composable
fun AlphabetScreen(textToSpeech: TextToSpeech) {
    val alphabet = ('A'..'Z').toList()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val alphabet = ('A'..'Z').toList()
        val chunkedAlphabet = alphabet.chunked(5) // Chunk list into groups of 5

        LazyColumn {
            items(chunkedAlphabet) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween // Distribute evenly
                ) {
                    for (letter in row) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    textToSpeech.speak(letter.toString(), TextToSpeech.QUEUE_ADD, null, null)
                                }
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = letter.toString())
                        }
                    }
                }
            }
        }
    }
}
