package com.example.registrationui.models

data class BillsPayItemModel(
    val id: Int,
    val title: String,
    val imageResourceId: String
)

data class BillsPayItemResponse(
    val members: List<BillsPayItemModel>
)