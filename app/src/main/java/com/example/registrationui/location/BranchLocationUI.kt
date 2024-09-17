package com.example.registrationui.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.example.registrationui.ui.GradientText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListUI(navController: NavHostController, title: String) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    val branches = listOf(
        LocationListData("Mirpur 10 Branch", "House 2, Raod 65, Mirpur 10, Dhaka"),
        LocationListData("Dhanmondi Branch", "House 10, Road 15, Dhanmondi, Dhaka"),
        LocationListData("Gulshan Branch", "House 25, Road 45, Gulshan 1, Dhaka"),
        LocationListData("Banani Branch", "House 12, Raod 34, Banani, Dhaka"),
        LocationListData("Mirpur 10 Branch", "House 2, Raod 65, Mirpur 10, Dhaka"),
        LocationListData("Dhanmondi Branch", "House 10, Road 15, Dhanmondi, Dhaka"),
        LocationListData("Gulshan Branch", "House 25, Road 45, Gulshan 1, Dhaka"),
        LocationListData("Banani Branch", "House 12, Raod 34, Banani, Dhaka")
    )



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
                        title,
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
                LocationListCardList(branches,title=title)

            }

        }
    }


}

@Composable
fun LocationListCardList(branches: List<LocationListData>,title:String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // Correctly pass a single BranchData to BranchCard
        items(branches) { branch ->
            LocationListCard(branch = branch, title = title)  // Pass a single BranchData object
        }
    }
}

@Composable
fun LocationListCard(branch: LocationListData,title:String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xffdfdfdf), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp), // Round corners

    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Branch Info Section
            Text(
                text = branch.name, // Dynamic branch name
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
                text = branch.address, // Dynamic branch address
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))

            // Action Buttons Section
            if (title=="Agent Banking"){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly // Distribute buttons evenly
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        AgentBankActionButton(iconRes = R.drawable.map_location, text = "Map")
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        AgentBankActionButton(iconRes = R.drawable.call_location, text = "Call")
                    }
                }
            }else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly // Distribute buttons evenly
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        ActionButton(iconRes = R.drawable.map_location, text = "Map")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        ActionButton(iconRes = R.drawable.mail_location, text = "Mail")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        ActionButton(iconRes = R.drawable.call_location, text = "Call")
                    }
                }
            }

        }
    }
}

// Data class for Branch information
data class LocationListData(
    val name: String,
    val address: String
)



@Composable
fun ActionButton(iconRes: Int, text: String) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(35.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xfff5f8ff), Color(0xfff5f8ff))
                ),
                shape = RoundedCornerShape(12.dp) // Slightly round the buttons
            ),

        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with rounded background
            Box(
                modifier = Modifier
                    .size(25.dp) // Adjust size as necessary
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                        )
                    ),
                contentAlignment = Alignment.Center // Align content (icon) in the center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp) // Icon size inside the circle
                        .padding(5.dp) // Padding inside the circle
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            // Text next to the icon
            GradientTextLocation(
                text = text,
                gradient = Brush.linearGradient(
                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                ),
                fontSize = 13.sp,
            )
        }

    }


}


@Composable
fun GradientTextLocation(
    text: String,
    gradient: Brush,
    fontSize: TextUnit,
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            brush = gradient
        ),
        modifier = Modifier.graphicsLayer(alpha = 0.99f)


    )
}


@Composable
fun AgentBankActionButton(iconRes: Int, text: String) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(35.dp).padding(start = 25.dp, end = 25.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xfff5f8ff), Color(0xfff5f8ff))
                ),
                shape = RoundedCornerShape(12.dp) // Slightly round the buttons
            ),

        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with rounded background
            Box(
                modifier = Modifier
                    .size(25.dp) // Adjust size as necessary
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                        )
                    ),
                contentAlignment = Alignment.Center // Align content (icon) in the center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp) // Icon size inside the circle
                        .padding(5.dp) // Padding inside the circle
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            // Text next to the icon
            GradientTextLocation(
                text = text,
                gradient = Brush.linearGradient(
                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                ),
                fontSize = 13.sp,
            )
        }

    }


}