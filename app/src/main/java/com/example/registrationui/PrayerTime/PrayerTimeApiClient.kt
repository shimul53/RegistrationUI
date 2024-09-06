package com.example.registrationui.PrayerTime

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PrayerTimeApiClient {
    private const val BASE_URL = "https://api.aladhan.com/v1/"

    fun create(): PrayerTimeApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PrayerTimeApiService::class.java)
    }
}
