package com.example.registrationui.models

import kotlinx.serialization.Serializable

@Serializable
data class BillWiseListItemModel(
    val id: Int,
    val title: String,
    val imageResourceId: String
)

@Serializable
data class BillWiseListItemModelResponse(
    val billNameWiseList: List<BillWiseListItemModel>
)