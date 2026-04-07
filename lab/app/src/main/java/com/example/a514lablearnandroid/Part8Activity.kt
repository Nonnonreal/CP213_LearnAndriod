package com.example.a514lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Part8Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResponsiveProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ResponsiveProfileScreen() {
    // 2 & 3. Using BoxWithConstraints to track dimension boundaries
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        if (maxWidth < 600.dp) {
            // Mobile (Portrait)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
            ) {
                ProfileImageComponent(modifier = Modifier.size(160.dp))
                ProfileInfoComponent(modifier = Modifier.fillMaxWidth())
            }
        } else {
            // Tablet or Mobile (Landscape) 
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                // Image stays fixed size
                ProfileImageComponent(modifier = Modifier.size(200.dp))
                // Info spans remaining space using weight
                ProfileInfoComponent(modifier = Modifier.weight(1f))
            }
        }
    }
}

// 1. Grey box component acting as profile picture
@Composable
fun ProfileImageComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile Picture Default",
            modifier = Modifier.fillMaxSize(0.6f),
            tint = Color.DarkGray
        )
    }
}

// 1. Text describing the profile info
@Composable
fun ProfileInfoComponent(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "John Doe", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Android Developer", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Passionate about creating modern, robust, and responsive user interfaces using Jetpack Compose.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
