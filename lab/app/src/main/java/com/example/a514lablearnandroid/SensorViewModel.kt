package com.example.a514lablearnandroid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SensorViewModel(application: Application) : AndroidViewModel(application) {

    private val hardwareTracker = HardwareTracker(application)

    // Accelerometer Data (X, Y, Z)
    private val _accelerometerData = MutableStateFlow(floatArrayOf(0f, 0f, 0f))
    val accelerometerData: StateFlow<FloatArray> = _accelerometerData.asStateFlow()

    // Location Data (Lat, Lng)
    private val _locationData = MutableStateFlow(Pair(0.0, 0.0))
    val locationData: StateFlow<Pair<Double, Double>> = _locationData.asStateFlow()

    fun startTrackingSensors() {
        hardwareTracker.startListeningSensors { values ->
            _accelerometerData.value = values.clone()
        }
    }

    fun stopTrackingSensors() {
        hardwareTracker.stopListeningSensors()
    }

    fun startTrackingLocation() {
        hardwareTracker.startListeningLocation { lat, lng ->
            _locationData.value = Pair(lat, lng)
        }
    }

    fun stopTrackingLocation() {
        hardwareTracker.stopListeningLocation()
    }

    override fun onCleared() {
        super.onCleared()
        hardwareTracker.stopListeningSensors()
        hardwareTracker.stopListeningLocation()
    }
}
