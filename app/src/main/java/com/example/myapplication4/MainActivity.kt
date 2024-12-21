package com.example.myapplication4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication4.ui.theme.MyApplication4Theme

class MainActivity : ComponentActivity() {

    init {
        System.loadLibrary("http") // Load the native library "libhttp.so"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        foo = { argc, argv -> foo(argc, argv) }
                    )
                }
            }
        }
    }

    // Declare the native function foo
    external fun foo(argc: Int, argv: Array<String>): Int
}

@Composable
fun Greeting(modifier: Modifier = Modifier, foo: (Int, Array<String>) -> Int) {
    var text by remember { mutableStateOf("Hello Android!") }

    // Column to arrange the Text and Button vertically
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the current text
        Text(
            text = text,
        )

        // Button to change the text and call the native function
        Button(onClick = {
            val argv = arrayOf("arg1", "arg2", "arg3")
            val result = foo(argv.size, argv) // Call the native function

            // Update the UI text with the result
            text = "JNI Result: $result"
        }) {
            Text("Call foo (JNI)")
        }
    }
}
