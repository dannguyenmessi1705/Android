package com.example.firstapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    private val _location = mutableStateOf<LocationDataInfo?>(null)
    val location: State<LocationDataInfo?> = _location

    fun updateLocation(newLocation: LocationDataInfo) {
        _location.value = newLocation
    }
}