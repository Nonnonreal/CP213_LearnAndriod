package com.example.a514lablearnandroid

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class Part6Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WebViewScreen()
                }
            }
        }
    }
}

// 1. ViewModel storing URL State
class WebViewModel : ViewModel() {
    var url by mutableStateOf("https://www.google.com")
        private set

    fun updateUrl(newUrl: String) {
        // Simple fix to ensure formatting has protocol scheme if omitted
        val formattedUrl = if (!newUrl.startsWith("http://") && !newUrl.startsWith("https://")) {
            "https://$newUrl"
        } else {
            newUrl
        }
        url = formattedUrl
    }
}

@Composable
fun WebViewScreen(viewModel: WebViewModel = viewModel()) {
    // Local state for the input field text before clicking 'Go'
    var inputText by remember { mutableStateOf(viewModel.url) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 4. URL Input Field and Go Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("URL") },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.updateUrl(inputText) }) {
                Text("Go")
            }
        }

        // 2 & 3. AndroidView embedding WebView
        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { context ->
                WebView(context).apply {
                    // This forces loading content inside the WebView rather than opening external browser app
                    webViewClient = WebViewClient()
                    
                    // Recommended setting to ensure modern pages load their js scripts
                    settings.javaScriptEnabled = true 
                }
            },
            // Constraints: demonstrate AndroidView update block
            update = { webView ->
                // The update block is called whenever the state object read inside changes.
                // Since viewModel.url is read here, Compose triggers it upon change.
                webView.loadUrl(viewModel.url)
            }
        )
    }
}
