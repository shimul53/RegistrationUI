package com.example.registrationui.models

import kotlinx.serialization.Serializable

@Serializable
data class BillDetail(
    val id: Int,
    val title: String,
    val imageResourceId: String
)

@Serializable
data class BillType(
    val id: Int,
    val title: String,
    val billList: List<BillDetail>
)

@Serializable
data class BillNameWiseListResponse(
    val billNameWiseList: List<BillType>
)
