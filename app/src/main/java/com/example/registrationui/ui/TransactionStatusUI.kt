package com.example.registrationui.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import downloadBtn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionStatus(navController: NavHostController) {
    val scope = rememberCoroutineScope()

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
                        "Transaction Status",
                        color = Color.Black,
                        fontSize = 16.sp
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
            contentAlignment = Alignment.Center // Center the content inside the Box
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.success_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                )

                ElevatedCardContent() // Call your elevated card composable here
                viewReceiptBtn(navController = navController)
            }

        }
    }
}

@Composable
fun ElevatedCardContent() {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column (modifier = Modifier

                .padding(top = 20.dp)) {
                // Content inside the Card
                Text(
                    text = "Your Fund Transfer is Successfully Done.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween // Space between the texts
                ) {
                    Text(text = "Transfer To", color = Color.Black, fontSize = 13.sp, modifier = Modifier.weight(1f).padding(start = 5.dp), textAlign = TextAlign.Start)
                    Text(text = "A/C 197623424332", color = Color.Black, fontSize = 13.sp,modifier = Modifier.weight(1f).padding(end = 5.dp),textAlign = TextAlign.End)
                }

                Divider(
                    color = Color.Gray.copy(alpha = 0.5f),
                    thickness = 1.dp,

                    )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween // Space between the texts
                ) {
                    Column (modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)){
                        Text(text = "Transfer To", color = Color.Black, fontSize = 12.sp, modifier = Modifier
                            .padding(2.dp), textAlign = TextAlign.Start)

                        Text(text = "Abu Darda", color = Color.Black, fontSize = 13.sp, modifier = Modifier
                            .padding(2.dp), textAlign = TextAlign.Start, fontWeight = FontWeight.Medium)

                        Text(text = "The City Bank LTD.", color = Color.Black, fontSize = 12.sp, modifier = Modifier
                            .padding(2.dp), textAlign = TextAlign.Start)
                    }


                    VerticalDivider(
                        color = Color.Gray.copy(alpha = 0.5f),
                        thickness = 1.dp, modifier = Modifier.height(98.dp)

                    )
                    Column (modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)){
                        Text(text = "Transfer To", color = Color.Black, fontSize = 12.sp, modifier = Modifier
                            .padding(2.dp), textAlign = TextAlign.Start)

                        Text(text = "BDT 5,000.00", color = Color.Black, fontSize = 13.sp, modifier = Modifier
                            .padding(2.dp), textAlign = TextAlign.Start, fontWeight = FontWeight.SemiBold)

                        Text(text = "Charge BDT 00.00", color = Color.Black, fontSize = 12.sp, modifier = Modifier
                            .padding(2.dp), textAlign = TextAlign.Start)
                    }

                }

                Divider(
                    color = Color.Gray.copy(alpha = 0.5f),
                    thickness = 1.dp,

                    )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),

                    horizontalArrangement = Arrangement.SpaceBetween // Space between the texts
                ) {

                    Text(text = "Date & Time", color = Color.Black, fontSize = 13.sp, modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp), textAlign = TextAlign.Start)
                    Text(text = "20-AUG-2024 | 01:35 PM", color = Color.Black, fontSize = 13.sp,modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),textAlign = TextAlign.End)
                }
            }
        }
    }
}

@Composable
fun viewReceiptBtn(navController: NavHostController) {

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth().height(100.dp)
            .padding(top = 30.dp, start = 30.dp, end = 30.dp, bottom = 20.dp),
        onClick = { navController.navigate("transactionReceipt") },
        border = BorderStroke(1.dp, Color(0xFF1c73c2)),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF1c73c2)
        )
    ) {
            Text(modifier = Modifier.padding(1.dp), color = Color.Black, text = "Get PDF Receipt", fontSize = 16.sp)

    }

}

