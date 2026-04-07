package com.example.a514lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Part1AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LikeButtonScreen()
                }
            }
        }
    }
}

@Composable
fun LikeButtonScreen() {
    var isLiked by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 1. Scale animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    // 2. Color animation
    val backgroundColor by animateColorAsState(
        targetValue = if (isLiked) Color(0xFFE91E63) else Color.Gray,
        label = "backgroundColor"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { isLiked = !isLiked },
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            modifier = Modifier.scale(scale),
            interactionSource = interactionSource
        ) {
            // 3. AnimatedVisibility for Icon
            AnimatedVisibility(visible = isLiked) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Liked",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(text = if (isLiked) "Liked" else "Like")
        }
    }
}
