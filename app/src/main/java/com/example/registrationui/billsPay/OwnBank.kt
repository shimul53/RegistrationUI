package com.example.registrationui.billsPay

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.example.registrationui.customFontFamily
import com.example.registrationui.loadDataFromJson.LoadDataFromJson
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OwnBank(navController: NavHostController) {
    val context = LocalContext.current
    val ownBankList = LoadDataFromJson().loadTransferInfo(context)
    var searchText by rememberSaveable { mutableStateOf("") }

    // Filtered list based on search text
    val filteredList = if (searchText.isEmpty()) {
        ownBankList
    } else {
        ownBankList.filter {
            it.receiverName.contains(searchText, ignoreCase = true) ||
                    it.toDisplayAccountNo.contains(searchText, ignoreCase = true)
        }
    }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    actionIconContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    scrolledContainerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                title = {
                    Text(
                        "Source Account",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 5.dp),
                color = Color.White,
                shape = RoundedCornerShape(30.dp),
                shadowElevation = 4.dp,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search Name",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    TextField(
                        singleLine = true,
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
                        ),
                        placeholder = {
                            Text(
                                text = "Search your beneficiary",
                                color = Color(0xff565353),
                                fontSize = 16.sp,
                                fontFamily = customFontFamily,
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        ),
                        keyboardActions = KeyboardActions(onSearch = {
                            // Handle search action here
                        })
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    items(filteredList.count()) { index ->
                        val showMenu = remember { mutableStateOf(false) }
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {
                                    navController.previousBackStackEntry?.savedStateHandle?.set(
                                        "beneficiaryAccount",
                                        filteredList[index]
                                    )
                                    navController.popBackStack()
                                },
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp),
                            shadowElevation = 1.dp,
                            border = BorderStroke(.5.dp, Color(0xffD7D7D7))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ellipse),
                                        contentDescription = "Test Image",
                                        modifier = Modifier
                                            .size(62.dp)
                                            .padding(end = 8.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .weight(3f)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = filteredList[index].receiverName,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp,
                                        fontFamily = customFontFamily,
                                        color = Color(0xff33384B)
                                    )
                                    Text(
                                        text = filteredList[index].toDisplayAccountNo,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        fontFamily = customFontFamily,
                                        color = Color(0xff858585)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1.1f)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.favorite),
                                            contentDescription = "Test Image",
                                            modifier = Modifier.size(30.dp),
                                            contentScale = ContentScale.Fit,
                                            colorFilter = ColorFilter.tint(Color(0xff014C8F))
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Image(
                                            painter = painterResource(id = R.drawable.dot),
                                            contentDescription = "Test Image",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clickable { showMenu.value = true },
                                            contentScale = ContentScale.Crop,
                                            colorFilter = ColorFilter.tint(Color(0xff787878))
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = showMenu.value,
                                        onDismissRequest = { showMenu.value = false },
                                        modifier = Modifier.background(Color.White)
                                    ) {
                                        DropdownMenuItem(text = {
                                            Text(
                                                "Update",
                                                fontFamily = customFontFamily,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }, onClick = {
                                            showMenu.value = false
                                        })
                                        DropdownMenuItem(text = {
                                            Text(
                                                "Delete",
                                                fontFamily = customFontFamily,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }, onClick = {
                                            showMenu.value = false
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }




}


