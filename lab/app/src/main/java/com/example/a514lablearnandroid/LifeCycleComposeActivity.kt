package com.example.a514lablearnandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class LifeCycleComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                LifecycleDemo(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun LifecycleDemo(modifier: Modifier = Modifier) {
    var show by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        Button(onClick = { show = !show }) {
            Text(if (show) "Hide" else "Show")
        }

        if (show) {
            LifecycleComponent()
        }
    }
}

@Composable
fun LifecycleComponent() {
    // State สำหรับ Recomposition
    var text by remember { mutableStateOf("") }

    // Log เมื่อ Recompose
    SideEffect {
        Log.d("ComposeLifecycle", "Recompose: $text")
    }

    // Log เมื่อ Enter/Leave
    DisposableEffect(Unit) {
        Log.d("ComposeLifecycle", "Enter Composition")
        onDispose {
            Log.d("ComposeLifecycle", "Leave Composition")
        }
    }

    Column {
        Text(text = "Unstable State: $text")
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Type to Recompose") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    Greeting("Android")
}