package com.example.registrationui.billsPay

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.example.registrationui.loadDataFromJson.LoadDataFromJson
import com.example.registrationui.models.BillWiseListItemModel
import com.example.registrationui.models.BillsPayItemModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsPayItemUI(navController: NavHostController, selectedTitle: String) {
    val context = LocalContext.current
    val members = LoadDataFromJson().LoadBillsPayItemDataFromJson(context)
    val member = LoadDataFromJson().loadBillsJson(context)

    // Track selected item by title
    val selectedItem = remember { mutableStateOf(selectedTitle) }

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentAlignment = Alignment.TopStart
        ) {
            Column {
                // Dynamically update the selected item
                selectedItem.value?.let { itemTitle ->
                    // Find the corresponding member from the list based on the selected title
                    val selectedMember = members.find { it.title == itemTitle }

                    selectedMember?.let {
                        // Convert the imageResourceId string back to an integer resource ID
                        val imageResourceId = it.imageResourceId.toInt()

                        // Pass the selected member for dynamic image and text
                        BillPayFirstSectionCard(
                            title = it.title,           // Use the selected item's title
                            subtitle = "Please select your ${it.title} Bill", // Dynamic subtitle
                            member = it.copy(imageResourceId = imageResourceId.toString())  // Convert back to string if needed
                        )
                    }
                }

                UtilitySelectionRow(
                    items = members,
                    selectedItem = selectedItem.value,
                    onSelect = { title ->
                        selectedItem.value = title // Update the selected title
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(1.dp)
                        .width(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))

                BillsList(bills = member) { bill ->
                    // Handle click here, e.g., navigate or display more details
                    Log.d("BillsPayItemUI", "${bill.title} clicked")
                }
            }
        }
    }
}


@Composable
fun BillPayFirstSectionCard(
    title: String,
    subtitle: String,
    member: BillsPayItemModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Text section
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
        }

        Divider(
            color = Color(0xfff3f3f6),
            modifier = Modifier
                .height(45.dp)
                .width(1.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))

        // Image section
        Card(
            modifier = Modifier
                .height(50.dp)
                .width(50.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFf8f8f8),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Convert imageResourceId string back to Int and load image
                val imageResourceId = member.imageResourceId.toIntOrNull() ?: R.drawable.gas_bills // Default image fallback
                Image(
                    painter = painterResource(id = imageResourceId),
                    contentDescription = "Bill Icon",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}





@Composable
fun UtilitySelectionRow(
    items: List<BillsPayItemModel>,
    selectedItem: String,
    onSelect: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Assume each item has a width of 100.dp
    val itemWidthDp = 100.dp
    val itemWidthPx = with(LocalDensity.current) { itemWidthDp.toPx() }
    val screenWidthPx = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }

    // Find the index of the selected item
    val selectedIndex = items.indexOfFirst { it.title == selectedItem }

    // Automatically scroll to the selected item
    if (selectedIndex != -1) {
    LaunchedEffect(selectedItem) {

            coroutineScope.launch {
                // Calculate the scroll offset to center the selected item
                val visibleItems = listState.layoutInfo.visibleItemsInfo
                val visibleItemCount = visibleItems.size
                val middleIndex = visibleItemCount / 2

                // Calculate the position to scroll to
                val centerOffsetPx = (screenWidthPx / 2) - (itemWidthPx / 2)
                val itemOffsetPx = selectedIndex * itemWidthPx
                val scrollOffset = (middleIndex - centerOffsetPx).toInt()

                // Scroll to the item
                listState.animateScrollToItem(index = selectedIndex, scrollOffset = scrollOffset)
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(items) { index, item ->
            Surface(
                shape = RoundedCornerShape(30),
                elevation = if (selectedItem == item.title) 8.dp else 0.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                OutlinedButton(
                    onClick = { onSelect(item.title) }, // Update the selection on click
                    shape = RoundedCornerShape(30),
                    border = BorderStroke(1.dp, if (selectedItem == item.title) Color(0xff014c8f) else Color.Transparent),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (selectedItem == item.title) Color.White else Color(0xFFf9f9f9),
                        contentColor = if (selectedItem == item.title) Color.Blue else Color.Gray
                    ),
                    modifier = Modifier
                        .height(35.dp)
                        .width(itemWidthDp) // Use dynamic width if needed
                ) {
                    Text(text = item.title)
                }
            }

            if (index < items.size - 1) {
                Divider(
                    color = Color(0xfff3f3f6),
                    modifier = Modifier
                        .height(10.dp)
                        .width(1.dp)
                )
            }
        }
    }
}






@Composable
fun BillsList(bills: List<BillWiseListItemModel>, onClick: (BillWiseListItemModel) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        items(bills) { bill ->
            BillsListCardItem(bill = bill, onClick = { onClick(bill) })
        }
    }
}

@Composable
fun BillsListCardItem(bill: BillWiseListItemModel, onClick: () -> Unit) {
    val context = LocalContext.current
    val imageResourceId = context.resources.getIdentifier(
        bill.imageResourceId.removePrefix("R.drawable."),
        "drawable",
        context.packageName
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            .clickable(onClick = onClick),
        elevation = 0.dp,
        border = BorderStroke(1.dp, Color(0xFFd7d7d7)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
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
                        painter = painterResource(id = imageResourceId),
                        contentDescription = bill.title,
                        modifier = Modifier.size(35.dp)
                    )
                }

                Text(
                    modifier = Modifier.padding(1.dp),
                    color = Color.Black,
                    text = bill.title,
                    fontSize = 16.sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Arrow Right",
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

