package com.example.registrationui.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransferInfoModel(
    @SerializedName("imageName") val imageName: String,
    @SerializedName("receiverName") val receiverName: String,
    @SerializedName("toAccountNo") val toAccountNo: String,
    @SerializedName("toBranchCode") val toBranchCode: String,
    @SerializedName("transferType") val transferType: String,
    @SerializedName("toDisplayAccountNo") val toDisplayAccountNo: String,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("availableBalance") val availableBalance: String
): Parcelable
