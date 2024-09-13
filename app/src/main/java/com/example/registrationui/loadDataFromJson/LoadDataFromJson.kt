package com.example.registrationui.loadDataFromJson

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.registrationui.models.BillWiseListItemModel
import com.example.registrationui.models.BillWiseListItemModelResponse
import com.example.registrationui.models.BillsPayItemModel
import com.example.registrationui.models.BillsPayItemResponse
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

class LoadDataFromJson {

    @Composable
    fun LoadBillsPayItemDataFromJson(context: Context): List<BillsPayItemModel> {
        var members by remember { mutableStateOf(emptyList<BillsPayItemModel>()) }

        LaunchedEffect(Unit) {
            // Load the JSON file from assets
            val inputStream = context.assets.open("billsPayItem.json")
            val reader = InputStreamReader(inputStream)

            // Parse the JSON into a BillsPayItemResponse object
            val response = Gson().fromJson(reader, BillsPayItemResponse::class.java)

            // Map imageResourceId from JSON string to actual drawable resource ID
            members = response.members.map { item ->
                item.copy(
                    imageResourceId = context.resources.getIdentifier(
                        item.imageResourceId.removePrefix("R.drawable."), // Remove "R.drawable."
                        "drawable",
                        context.packageName
                    ).toString() // Convert resource ID back to string
                )
            }

            reader.close()
        }

        return members
    }

    /* @Composable
    fun loadBillsJson(context: Context): BillWiseListItemModelResponse {
        val jsonString = context.assets.open("billsListItem.json").bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)*/


    @Composable
    fun loadBillsJson(context: Context): List<BillWiseListItemModel> {
        var billNameWiseList by remember { mutableStateOf(emptyList<BillWiseListItemModel>()) }

        LaunchedEffect(Unit) {
            // Load the JSON file from assets
            val inputStream = context.assets.open("billsListItem.json")
            val reader = InputStreamReader(inputStream)

            // Parse the JSON into a BillsPayItemResponse object
            val response = Gson().fromJson(reader, BillWiseListItemModelResponse::class.java)

            // Map imageResourceId from JSON string to actual drawable resource ID
            billNameWiseList = response.billNameWiseList.map { item ->
                item.copy(
                    imageResourceId = context.resources.getIdentifier(
                        item.imageResourceId.removePrefix("R.drawable."), // Remove "R.drawable."
                        "drawable",
                        context.packageName
                    ).toString() // Convert resource ID back to string
                )
            }

            reader.close()
        }

        return billNameWiseList
    }



    fun getImageResourceByName(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}