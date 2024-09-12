package com.example.registrationui.models

data class BillWiseListItemModel(
    val id: Int,
    val title: String,
    val imageResourceId: Int
)

data class BillWiseListItemModelResponse(
    val members: List<BillWiseListItemModel>
)