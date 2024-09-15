package com.example.registrationui.models

data class LocationItemModel(
    val id: Int,
    val title: String,
    val imageResourceId: String
)

data class LocationItemResponse(
    val members: List<LocationItemModel>
)