package com.example.registrationui.PrayerTime

import com.example.registrationui.models.PrayerTimesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerTimeApiService {
    @GET("timings")
    suspend fun getPrayerTimes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int = 4
    ): PrayerTimesResponse
}
