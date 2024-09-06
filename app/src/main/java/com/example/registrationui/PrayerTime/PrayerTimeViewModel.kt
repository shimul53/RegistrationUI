package com.example.registrationui.PrayerTime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrationui.models.PrayerData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PrayerTimesViewModel : ViewModel() {
    private val _prayerTimes = MutableStateFlow<PrayerData?>(null)
    val prayerTimes: StateFlow<PrayerData?> = _prayerTimes

    private val apiService = PrayerTimeApiClient.create()

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
