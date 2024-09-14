package com.example.registrationui.billsPay

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.example.registrationui.loadDataFromJson.LoadDataFromJson
import com.example.registrationui.ui.GradientText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsPaymentScreen(navController: NavHostController, selectedTitle: String) {





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
                        selectedTitle,
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

            Column {
                BillPaymentContent(Modifier.padding(paddingValues),navController=navController)
            }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillPaymentContent(modifier: Modifier = Modifier,navController: NavHostController) {

    val billNumberState = remember { mutableStateOf("") }
    val billAmountState = remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // To make the screen scrollable
    ) {
        // Source Account Label
        Text(
            text = "Source Account",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown to select account
        var expanded by remember { mutableStateOf(false) }
        var selectedAccount by remember { mutableStateOf("Select Your Account") }

        AccountDropdown()

        Spacer(modifier = Modifier.height(8.dp))

        // Available Balance
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Available Balance",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "00,000.00",
                style = MaterialTheme.typography.body2,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bill No Field

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

            ) {
            Text(
                text = "Bill No", textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold
            )


            GradientText(
                text = "Sample",
                gradient = Brush.linearGradient(
                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                ),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        androidx.compose.material3.OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = billNumberState.value, onValueChange = { billNumberState.value = it },
            placeholder = { Text(text = "Enter Your Bill Number", color = Color.Black, fontSize = 14.sp )},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color(0x54545454), // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray,
                containerColor = Color(0xFBFBFBFB),
            ),
        )



        Spacer(modifier = Modifier.height(24.dp))

        // Amount Field
        Text(
            text = "Amount",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = billAmountState.value, onValueChange = { billAmountState.value = it },
            placeholder = { Text(text = "Enter Bill Amount" , color = Color.Black, fontSize = 14.sp)},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF014C8F),
                unfocusedBorderColor = Color(0x54545454), // Optional: define the unfocused border color
                cursorColor = Color(0xFF014C8F),         // Cursor color
                focusedLabelColor = Color(0xFF014C8F),   // Focused label color
                unfocusedLabelColor = Color.Gray,
                containerColor = Color(0xFBFBFBFB),
            ),
        )



        Spacer(modifier = Modifier.height(24.dp))
        nextBtn(navController = navController)


    }
}



@Composable
fun nextBtn(navController: NavHostController) {
    androidx.compose.material3.Button(
        onClick = {
            // navController.navigate("otp")
        },
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // Set background to transparent to use gradient
        ),
        contentPadding = PaddingValues(), // Remove padding to match gradient size
        modifier = Modifier
            .height(53.dp)
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                ),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = "Next",
            fontSize = 20.sp,
            color = Color.White // Text color
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDropdown() {
    var expanded by remember { mutableStateOf(false) } // Dropdown state
    var selectedAccount by remember { mutableStateOf("Select Your Account") } // Current selection

    val accountOptions = listOf("Account 1", "Account 2", "Account 3") // Sample data

    // Surface to hold the dropdown and apply styles
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color(0xfffafafa),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color(0xffb5b5b5))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
        ) {
            // ExposedDropdownMenuBox for better dropdown handling
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                // TextField for showing the selected account with trailing icon
                TextField(
                    textStyle = TextStyle( fontSize = 14.sp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    readOnly = true,
                    value = selectedAccount, // Display selected account
                    onValueChange = {},
                    trailingIcon = {
                        val icon = R.drawable.baseline_expand_more_24 // Custom icon when dropdown is expanded


                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = Color(0xffB5B5B5), // Customize the icon color as needed
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterVertically)
                                .offset(x = (-7).dp)
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedTextColor = Color(0xff565353),
                        unfocusedTextColor = Color(0xff565353),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom underline
                        unfocusedIndicatorColor = Color.Transparent // Remove bottom underline
                    ),
                )

                // Dropdown menu to show the account options
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xfffafafa))
                ) {
                    accountOptions.forEach { account ->
                        DropdownMenuItem(

                            content = {
                                Text(
                                    account,
                                    color = Color.Black,fontSize = 14.sp // Customize text color
                                )
                            },
                            onClick = {
                                selectedAccount = account // Update selected account
                                expanded = false // Close the dropdown
                            },

                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,

                        )
                    }
                }
            }
        }
    }
}








