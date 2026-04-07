package com.example.a514lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class Part5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SnackbarEffectScreen()
            }
        }
    }
}

// 1. ViewModel with one-time event (Channel)
class SnackbarViewModel : ViewModel() {
    // Channel is perfect for side effects since it guarantees delivery strictly once.
    private val _errorChannel = Channel<String>()
    val errorFlow = _errorChannel.receiveAsFlow()

    fun triggerError() {
        viewModelScope.launch {
            val randomErrorCode = (100..500).random()
            _errorChannel.send("Network Error $randomErrorCode: Something went wrong!")
        }
    }
}

@Composable
fun SnackbarEffectScreen(viewModel: SnackbarViewModel = viewModel()) {
    // 2. Setup SnackbarHostState for Scaffold
    val snackbarHostState = remember { SnackbarHostState() }

    // 3. Observe the Flow inside LaunchedEffect
    // We use Unit or viewModel as key so it persists and continually collects events
    LaunchedEffect(viewModel) {
        viewModel.errorFlow.collect { errorMessage ->
            // Calling this suspend function shows the UI and suspends until it dismisses
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Side-Effect Snackbar Demo",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Observe Channel Events via LaunchedEffect",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                // 4. Trigger Error button
                Button(onClick = { viewModel.triggerError() }) {
                    Text("Trigger Error")
                }
            }
        }
    }
}
