package com.example.registrationui.location

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.example.registrationui.loadDataFromJson.LoadDataFromJson
import com.example.registrationui.models.LocationItemModel
import com.example.registrationui.models.LocationItemResponse
import com.example.registrationui.models.PrayerTimeItemModel
import com.example.registrationui.models.PrayerTimeItemResponse
import com.example.registrationui.ui.loadDataFromJson
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStreamReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindMeItemUI(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val members = LoadDataFromJson().loadLocationItemDataFromJson(context)


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
                        "Find Me",
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


                LocationItem(navController = navController,members=members)





            }

        }
    }


}





@Composable
fun LocationItem(
    navController: NavHostController,
    members: List<LocationItemModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                            .height(120.dp)
                            .width(1.dp)
                            .padding(top = 20.dp)
                    )
                }
                LocationCardItem(
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
                    LocationCardItem(
                        member = members[3], // "Corporate Office"
                        navController = navController,
                        modifier = Modifier.weight(1f) // Takes up same space as first row items
                    )
                }

                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(120.dp)
                        .width(1.dp)
                )

                // Display "Agent Banking" if available
                if (members.size > 4) {
                    LocationCardItem(
                        member = members[4], // "Agent Banking"
                        navController = navController,
                        modifier = Modifier.weight(1f) // Takes up same space as first row items
                    )
                }

                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(120.dp)
                        .width(1.dp)
                )

                // Fill an empty space to align with the third item in the first row
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
fun LocationCardItem(
    member: LocationItemModel,
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
                navController.navigate("locationItemUI/${member.title}")

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


            val imageResourceId = member.imageResourceId.toIntOrNull() ?: R.drawable.default_image // Default fallback image
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

