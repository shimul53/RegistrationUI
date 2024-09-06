package com.example.registrationui.models

data class PrayerTimeItemModel(
    val id: Int,
    val title: String,
    val subTitle: String
)

data class PrayerTimeItemResponse(
    val members: List<PrayerTimeItemModel>
)