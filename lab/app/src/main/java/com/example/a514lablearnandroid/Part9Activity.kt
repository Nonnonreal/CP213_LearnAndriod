package com.example.a514lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

class Part9Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CollapsingToolbarScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarScreen() {
    // 1. Define the scroll behavior for Collapsing effect
    // 'exitUntilCollapsed' shrinks the App Bar when scrolling down, and expands it when scrolling back up.
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    Scaffold(
        // 2. Attach the nested scroll connection to the Scaffold.
        // This links the scrolling action of the inner LazyColumn directly to the AppBar.
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Collapsing Toolbar (Part 9)") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            // Explanation Text
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Concept of Collapsing Toolbar",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "A Collapsing Toolbar provides a responsive header that adapts to user scrolling dynamically. \n\n" +
                                   "By utilizing 'TopAppBarScrollBehavior' and passing it to a 'LargeTopAppBar' (or 'MediumTopAppBar'), " +
                                   "Jetpack Compose listens to nested scroll events from scrollable lists like 'LazyColumn'. \n\n" +
                                   "When the user scrolls down, the App Bar smoothly shrinks to provide more screen space for the content. " +
                                   "Scrolling back up incrementally reveals the expanded view again.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            
            // Generate dummy items so the user has something to scroll
            items(20) { index ->
                Text(
                    text = "Dummy List Item ${index + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}
