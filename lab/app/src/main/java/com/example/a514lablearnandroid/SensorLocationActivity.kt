package com.example.a514lablearnandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

class SensorLocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SensorLocationScreen()
                }
            }
        }
    }
}

@Composable
fun SensorLocationScreen(viewModel: SensorViewModel = viewModel()) {
    val context = LocalContext.current

    // Collect states from ViewModel
    val accelerometerData by viewModel.accelerometerData.collectAsState()
    val locationData by viewModel.locationData.collectAsState()

    var locationPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        locationPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (locationPermissionGranted) {
            viewModel.startTrackingLocation()
        }
    }

    // Start tracking sensors when screen is visible, stop when hidden
    DisposableEffect(Unit) {
        viewModel.startTrackingSensors()
        if (locationPermissionGranted) {
            viewModel.startTrackingLocation()
        }
        onDispose {
            viewModel.stopTrackingSensors()
            viewModel.stopTrackingLocation()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sensor & Location MVVM", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(32.dp))

        // Accelerometer Section
        Text(text = "Accelerometer Data:", fontWeight = FontWeight.SemiBold)
        Text(text = "X: ${String.format("%.2f", accelerometerData[0])}")
        Text(text = "Y: ${String.format("%.2f", accelerometerData[1])}")
        Text(text = "Z: ${String.format("%.2f", accelerometerData[2])}")

        Spacer(modifier = Modifier.height(32.dp))

        // Location Section
        Text(text = "Location Data:", fontWeight = FontWeight.SemiBold)
        if (locationPermissionGranted) {
            Text(text = "Lat: ${String.format("%.5f", locationData.first)}")
            Text(text = "Lng: ${String.format("%.5f", locationData.second)}")
        } else {
            Text(text = "Location Permission Required")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }) {
                Text("ขออนุญาต Location")
            }
        }
    }
}
