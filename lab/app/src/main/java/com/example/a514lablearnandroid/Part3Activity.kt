package com.example.a514lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

class Part3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DonutChartScreen()
                }
            }
        }
    }
}

@Composable
fun DonutChartScreen() {
    // 1. Mock parameters to fulfill requirements
    val proportions = listOf(30f, 40f, 30f)
    val sliceColors = listOf(Color(0xFFFF5722), Color(0xFF4CAF50), Color(0xFF2196F3))
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Donut Chart Animation", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(32.dp))
        DonutChart(
            proportions = proportions,
            colors = sliceColors,
            modifier = Modifier.size(240.dp)
        )
    }
}

@Composable
fun DonutChart(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    // Calculate total sum of proportions
    val total = proportions.sum()
    val proportionsAsAngles = proportions.map { 360f * (it / total) }
    
    // 3. Sweep Angle Animation State
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 1500)
        )
    }

    // 2. drawArc inside Canvas
    Canvas(modifier = modifier) {
        var startAngle = -90f // Start from top
        val currentProgress = animatedProgress.value
        
        for (i in proportionsAsAngles.indices) {
            val sweepAngle = proportionsAsAngles[i]
            
            val sliceStartProgress = startAngle + 90f // Adjusted to start from 0 progress
            val sliceEndProgress = sliceStartProgress + sweepAngle
            
            // Calculate visible sweep angle based on animation progress
            val visibleSweepAngle = if (currentProgress >= sliceEndProgress) {
                sweepAngle
            } else if (currentProgress > sliceStartProgress) {
                currentProgress - sliceStartProgress
            } else {
                0f
            }
            
            if (visibleSweepAngle > 0) {
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = visibleSweepAngle,
                    useCenter = false,
                    style = Stroke(width = 45.dp.toPx(), cap = StrokeCap.Butt)
                )
            }
            startAngle += sweepAngle
        }
    }
}
