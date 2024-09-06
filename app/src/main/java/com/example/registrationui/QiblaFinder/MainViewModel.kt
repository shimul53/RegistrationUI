package com.example.registrationui.QiblaFinder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrationui.PrayerTime.PrayerTimeApiClient
import com.example.registrationui.models.PrayerData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

    private val _qiblaState = MutableStateFlow(QiblaState())
    val qiblaState = _qiblaState.asStateFlow()

    private val _prayerTimes = MutableStateFlow<PrayerData?>(null)
    val prayerTimes: StateFlow<PrayerData?> = _prayerTimes
    private val apiService = PrayerTimeApiClient.create()

    // Other properties and methods...

    fun updateQiblaDirection(newDirection: Float) {
        _qiblaState.update {
            it.copy(
                qiblaDirection = newDirection
            )
        }
        Log.d("ViewModel direction", "Updating Qibla Direction to $newDirection")
    }

    fun updateCurrentDirection(newDirection: Float) {
        _qiblaState.update {
            it.copy(
                currentDirection = newDirection
            )
        }
        Log.d("ViewModel direction", "Updating currentDirection to $newDirection")
    }



    fun fetchPrayerTimes(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = apiService.getPrayerTimes(latitude, longitude)
                if (response.code == 200) {
                    _prayerTimes.value = response.data
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

}