package com.example.registrationui.billsPay

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.registrationui.R
import com.example.registrationui.loadDataFromJson.LoadDataFromJson
import com.example.registrationui.location.LocationCardItem
import com.example.registrationui.models.BillsPayItemModel
import com.example.registrationui.models.BillsPayItemResponse
import com.example.registrationui.models.LocationItemModel
import com.example.registrationui.models.LocationItemResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStreamReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsPayUI(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val members = LoadDataFromJson().LoadBillsPayItemDataFromJson(context)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedTitle = navBackStackEntry?.arguments?.getString("selectedTitle") ?: ""


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


                BillsPayItem(navController = navController,members=members)
                Divider(color = Color(0xfff3f3f6),modifier = Modifier
                    .height(1.dp)
                    .width(100.dp)
                    .align(Alignment.CenterHorizontally))
                SavedBillCard(onClick = {})
                BillsHistoryCard(onClick = {})





            }

        }
    }


}






@Composable
fun BillsPayItem(
    navController: NavHostController,
    members: List<BillsPayItemModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 50.dp)
    ) {
        // First row with up to 3 items
        val firstRowItems = members.take(3) // Get up to 3 items safely

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min), // To ensure Divider stretches full height
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            firstRowItems.forEachIndexed { index, member ->
                if (index > 0) {
                    Divider(
                        color = Color(0xfff3f3f6),
                        modifier = Modifier
                            .height(110.dp)
                            .width(1.dp)
                            .padding(top = 20.dp)
                    )
                }
                BillsPayCardItem(
                    member = member,
                    navController = navController,
                    modifier = Modifier.weight(1f) // Each item takes equal space
                )
            }
        }

        // Horizontal divider between the rows
        if (members.size > 3) {
            Divider(
                color = Color(0xfff3f3f6),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }

        // Second row with 2 items (if available), with equal space like first row
        if (members.size > 3) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min), // To ensure Divider stretches full height
                horizontalArrangement = Arrangement.SpaceBetween // Spread items across full width
            ) {
                // Display "Corporate Office" if available
                if (members.size > 3) {
                    BillsPayCardItem(
                        member = members[3], // "Corporate Office"
                        navController = navController,
                        modifier = Modifier.weight(1f) // Takes up same space as first row items
                    )
                }

                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(110.dp)
                        .width(1.dp)
                )

                // Display "Agent Banking" if available
                if (members.size > 4) {
                    BillsPayCardItem(
                        member = members[4], // "Agent Banking"
                        navController = navController,
                        modifier = Modifier.weight(1f) // Takes up same space as first row items
                    )
                }

                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(110.dp)
                        .width(1.dp)
                )

                if (members.size > 5) {
                    BillsPayCardItem(
                        member = members[5], // "Agent Banking"
                        navController = navController,
                        modifier = Modifier.weight(1f) // Takes up same space as first row items
                    )
                }


            }
        }

        // Horizontal divider between the rows
        if (members.size > 3) {
            Divider(
                color = Color(0xfff3f3f6),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }

        if (members.size > 5) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min), // To ensure Divider stretches full height
                horizontalArrangement = Arrangement.SpaceBetween // Spread items across full width
            ) {
                // Display "Corporate Office" if available
                if (members.size > 5) {
                    BillsPayCardItem(
                        member = members[6], // "Corporate Office"
                        navController = navController,
                        modifier = Modifier.weight(1f) // Takes up same space as first row items
                    )
                }

                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(100.dp)
                        .width(1.dp)
                )



                // Fill an empty space to align with the third item in the first row
                Spacer(modifier = Modifier.weight(2f))
            }
        }
    }
}




@Composable
fun BillsPayCardItem(
    member: BillsPayItemModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                // Navigate to the BillsPayItemUI screen with the selected item title
                navController.navigate("billsPayItemUI/${member.title}")
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
        ) {
            // Dynamically resolve the image resource from member
            val imageResourceId = member.imageResourceId.toIntOrNull() ?: R.drawable.gas_bills // Default fallback image

            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = member.title, fontSize = 14.sp, textAlign = TextAlign.Center)
    }
}






@Composable
fun SavedBillCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
            .clickable(onClick = onClick), // Handle clicks
        elevation = 0.dp, // No elevation to keep it flat like an outlined button
        border = BorderStroke(1.dp, Color(0xFFd7d7d7)), // Border color and width
        shape = RoundedCornerShape(8.dp) // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ) // Inner padding for the content
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .height(45.dp)
                        .width(60.dp)
                        .background(
                            color = Color(0xFFe6edf4),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.saved_bill),
                        contentDescription = "Saved Bill Icon",
                        modifier = Modifier
                            .size(35.dp)
                    )
                }

                Text(
                    modifier = Modifier.padding(1.dp),
                    color = Color.Black,
                    text = "Saved Bill",
                    fontSize = 16.sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Arrow Right",
                modifier = Modifier
                    .size(25.dp)
            )
        }
    }
}
@Composable
fun BillsHistoryCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            .clickable(onClick = onClick), // Handle clicks
        elevation = 0.dp, // No elevation to keep it flat like an outlined button
        border = BorderStroke(1.dp, Color(0xFFd7d7d7)), // Border color and width
        shape = RoundedCornerShape(8.dp) // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ) // Inner padding for the content
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .height(45.dp)
                        .width(60.dp)
                        .background(
                            color = Color(0xFFe6edf4),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bill_history),
                        contentDescription = "Bill History Icon",
                        modifier = Modifier
                            .size(35.dp)
                    )
                }

                Text(
                    modifier = Modifier.padding(1.dp),
                    color = Color.Black,
                    text = "Bills History",
                    fontSize = 16.sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Arrow Right",
                modifier = Modifier
                    .size(25.dp)
            )
        }
    }
}



/*
@Composable
fun savedBillBtn() {

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
        onClick = { */
/* Do something *//*
 },
        border = BorderStroke(1.dp, Color(0xFFd7d7d7)),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF000000)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .height(45.dp) // Size of the background box
                        .width(60.dp)
                        .background(
                            color = Color(0xFFe6edf4), // Background color
                            shape = RoundedCornerShape(8.dp) // Rounded corners for the rectangular shape
                        ),
                    contentAlignment = Alignment.Center // Align the image in the center of the box
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.saved_bill),
                        contentDescription = "Account Icon",
                        modifier = Modifier
                            .size(35.dp) // Image size
                    )
                }


                Text(
                    modifier = Modifier.padding(1.dp),
                    color = Color.Black,
                    text = "Saved Bill",
                    fontSize = 16.sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Account Icon",
                modifier = Modifier
                    .size(25.dp)

            )
        }
    }

}

@Composable
fun billsHistoryBtn() {

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        onClick = { */
/* Do something *//*
 },
        border = BorderStroke(1.dp, Color(0xFFd7d7d7)),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF000000)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .height(45.dp) // Size of the background box
                        .width(60.dp)
                        .background(
                            color = Color(0xFFe6edf4), // Background color
                            shape = RoundedCornerShape(8.dp) // Rounded corners for the rectangular shape
                        ),
                    contentAlignment = Alignment.Center // Align the image in the center of the box
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bill_history),
                        contentDescription = "Account Icon",
                        modifier = Modifier
                            .size(35.dp) // Image size
                    )
                }


                Text(
                    modifier = Modifier.padding(1.dp),
                    color = Color.Black,
                    text = "Bills History",
                    fontSize = 16.sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Account Icon",
                modifier = Modifier
                    .size(25.dp)

            )
        }
    }

}
*/
