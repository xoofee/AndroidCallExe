package com.example.myapplication4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication4.ui.theme.MyApplication4Theme
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.util.Log
import java.io.File
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    // State to hold the text value
    var text by remember { mutableStateOf("Hello Android!") }
    val context = LocalContext.current

    // Column to arrange the Text and Button vertically
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Displaying the current text
        Text(
            text = text,
        )

        // Button to change the text
        Button(onClick = {
            text = "Hello, Compose!" // Change the text when clicked

            runExecutable(context)


        }) {
            Text("Change Text")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplication4Theme {
        Greeting()
    }
}

// Function to extract and run the ARM64 ELF executable
fun runExecutable(context: Context) {
    try {
        // Get the executable from assets
        val assetManager = context.assets
        val inputStream = assetManager.open("http")

        // Create a temporary file in the internal storage
        val tempFile = File(context.filesDir, "http")

        // Check if the file already exists, if not, write the content to it
        if (!tempFile.exists()) {
            tempFile.writeBytes(inputStream.readBytes())
        }

        // Make the file executable if it is not already
        if (!tempFile.canExecute()) {
            val result = tempFile.setExecutable(true)
            if (!result) {
                Log.e("ExecutableError", "Failed to make the file executable")
                return
            }
        }

        // Run the executable using ProcessBuilder
//        val processBuilder = ProcessBuilder(tempFile.absolutePath)    // no permission
        val processBuilder = ProcessBuilder("/system/bin/linker64", tempFile.absolutePath)
        processBuilder.redirectErrorStream(true)

        // Start the process and capture output
        val process = processBuilder.start()
        val reader = process.inputStream.bufferedReader()
        val output = reader.readText()

        // Log the output
        Log.d("ExecutableOutput", output)

        // Update UI with result (you can customize how you want to handle it)
        // Note: UI updates should ideally happen on the main thread
        // For this example, we're assuming we're updating it correctly.

    } catch (e: IOException) {
        Log.e("ExecutableError", "Error running executable", e)
    }
}