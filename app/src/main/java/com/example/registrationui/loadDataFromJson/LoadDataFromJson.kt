package com.example.registrationui.loadDataFromJson

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.registrationui.R
import com.example.registrationui.models.BillDetail
import com.example.registrationui.models.BillNameWiseListResponse
import com.example.registrationui.models.BillType
import com.example.registrationui.models.BillsPayItemModel
import com.example.registrationui.models.BillsPayItemResponse
import com.example.registrationui.models.LocationItemModel
import com.example.registrationui.models.LocationItemResponse
import com.example.registrationui.models.TransferInfoModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

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
    fun loadBillsJson(context: Context): List<BillType> {
        val billTypesState = remember { mutableStateOf<List<BillType>>(emptyList()) }

        LaunchedEffect(Unit) {
            try {
                withContext(Dispatchers.IO) {
                    val inputStream = context.assets.open("billsListItem.json")
                    val reader = InputStreamReader(inputStream)

                    // Parse the JSON into a BillNameWiseListResponse object
                    val response = Gson().fromJson(reader, BillNameWiseListResponse::class.java)

                    // Map imageResourceId from JSON string to actual drawable resource ID
                    val billTypes = response.billNameWiseList.map { billType ->
                        billType.copy(
                            billList = billType.billList.map { billDetail ->
                                val resourceId = context.resources.getIdentifier(
                                    billDetail.imageResourceId.split(".").last(), // Get the actual image name
                                    "drawable",
                                    context.packageName
                                )
                                billDetail.copy(
                                    imageResourceId = if (resourceId != 0) resourceId.toString() else R.drawable.default_image.toString() // Fallback to a default image
                                )
                            }
                        )
                    }

                    // Update the state with the processed list
                    billTypesState.value = billTypes

                    // Close the reader
                    reader.close()
                }
            } catch (e: Exception) {
                Log.e("loadBillsJson", "Error loading bills JSON", e)
            }
        }

        return billTypesState.value
    }


    @Composable
    fun loadLocationItemDataFromJson(context: Context): List<LocationItemModel> {
        var members by remember { mutableStateOf(emptyList<LocationItemModel>()) }

        LaunchedEffect(Unit) {
            // Load the JSON file from assets
            val inputStream = context.assets.open("locationItem.json")
            val reader = InputStreamReader(inputStream)

            // Parse the JSON into a BillsPayItemResponse object
            val response = Gson().fromJson(reader, LocationItemResponse::class.java)

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

    fun loadTransferInfo(context: Context): List<TransferInfoModel> {
        return try {
            // Open the JSON file from the assets folder
            val inputStream = context.assets.open("own.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            // Define the type for the list of TransferInfoModel
            val listType = object : TypeToken<List<TransferInfoModel>>() {}.type

            // Parse the JSON string into a list of TransferInfoModel objects
            Gson().fromJson(jsonString, listType)
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }




}