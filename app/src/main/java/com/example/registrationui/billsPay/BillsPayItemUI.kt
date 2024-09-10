package com.example.registrationui.billsPay

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsPayItemUI(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val members = LoadBillsPayItemDataFromJson(context)


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
                        "Bills Pay",
                        color = Color.Black,
                        fontSize = 18.sp, fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { navController.popBackStack() } }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentAlignment = Alignment.TopStart
        ) {
            Column {

                UtilitySelectionRow()






            }

        }
    }


}


@Composable
fun UtilitySelectionRow() {
    val items = listOf("Electricity", "Gas", "Water","Internet", "E-Service", "Telephone","Education")
    val selectedItem = remember { mutableStateOf("Electricity") }

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = { selectedItem.value = item },
                shape = RoundedCornerShape(30), // Rounded corners like a pill
                border = BorderStroke(1.dp, if (selectedItem.value == item) Color(0xff014c8f) else Color.Transparent),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (selectedItem.value == item) Color.White else Color(0xFFf9f9f9), // Change background based on selection
                    contentColor = if (selectedItem.value == item) Color.Blue else Color.Gray
                ),
                modifier = Modifier
                    .padding(start =  8.dp , end = 8.dp ).height(35.dp).width(100.dp) // Padding for spacing between buttons
            ) {
                Text(text = item)
            }

            // Divider between buttons (except after the last one)
            if (index < items.size - 1) {
                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(10.dp) // Adjust height to match button size
                        .width(1.dp)

                )
            }
        }
    }
}


