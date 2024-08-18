package com.example.registrationui.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.registrationui.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()

    // Track the currently expanded card's header
    var expandedCard by remember { mutableStateOf<String?>(null) }

    // State for search query
    var searchQuery by rememberSaveable { mutableStateOf("") }

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
                    androidx.compose.material3.Text(
                        "FAQ",
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = { scope.launch { navController.popBackStack() } }) {
                        androidx.compose.material3.Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            contentAlignment = Alignment.TopCenter // Align content to the top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "How can we help you?",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // SearchBar component
                SearchBar(
                    hint = "Search Frequently ask questions ?",
                    onTextChange = { query ->
                        searchQuery = query
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Filtered ExpandableCard components
                val faqList = listOf(
                    "How to signup?" to "One of the Five Pillars of Islam, Zakat is a fundamental part of being a Muslim. Meaning purification, Zakat is the act of giving a set percentage of your total wealth to charity each lunar year and is required of every sane, adult Muslim whose accumulated wealth exceeds the current Zakat nisab value. Zakat is so much more than just giving to those in need; paying Zakat is an act of worship.",
                    "General Information!" to "Description for Card 2",
                    "What is the benefit of using this app?" to "Description for Card 3",
                    "Which is the best banking app in BD?" to "Description for Card 4",
                    "How to pay your bills with this app?" to "Description for Card 5"
                )

                val filteredFaqList = faqList.filter {
                    it.first.contains(searchQuery, ignoreCase = true)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Display filtered FAQ cards
                    filteredFaqList.forEach { (header, description) ->
                        ExpandableCard(
                            header = header,
                            description = description,
                            color = Color(0x54545454),
                            isExpanded = expandedCard == header,
                            onCardArrowClick = { expandedCard = if (expandedCard == header) null else header }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    // Handle case when no FAQs match the search query
                    if (filteredFaqList.isEmpty()) {
                        Text(
                            text = "No results found",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }


            }
        }
    }
}



@Composable
fun SearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    isEnabled: (Boolean) = true,
    height: Dp = 60.dp,
    elevation: Dp = 10.dp,
    cornerShape: Shape = RoundedCornerShape(30.dp),
    backgroundColor: Color = Color.White,
    onSearchClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .shadow(elevation = elevation, shape = cornerShape)
            .background(color = backgroundColor, shape = cornerShape)
            .clickable { onSearchClicked() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = modifier
                .weight(6f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = text,
            onValueChange = {
                text = it
                onTextChange(it.text)
            },
            enabled = isEnabled,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
            ),
            decorationBox = { innerTextField ->
                if (text.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = Color.Gray.copy(alpha = 0.5f),
                        fontSize = 16.sp,
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = androidx.compose.ui.text.input.ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
            singleLine = true
        )
        Box(
            modifier = modifier
                .weight(1f)
                .size(40.dp)
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    if (text.text.isNotEmpty()) {
                        text = TextFieldValue(text = "")
                        onTextChange("")
                    }
                },
        ) {
            if (text.text.isNotEmpty()) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.baseline_clear_24 ),
                    contentDescription = "clear",
                    tint = Color.Black,
                )
            } else {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "search",
                    tint =  Color.Gray.copy(alpha = 0.5f),
                )
            }
        }
    }
}


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ExpandableCard(
        header: String,
        description: String,
        color: Color,
        isExpanded: Boolean, // Whether the card is expanded or not
        onCardArrowClick: () -> Unit // Callback when the card is clicked
    ) {
        val rotationState by animateFloatAsState(
            targetValue = if (isExpanded) 180f else 0f,
            label = "Rotation state of expand icon button",
        )
        val strokeState by animateDpAsState(
            targetValue = if (isExpanded) 2.dp else 1.dp,
            label = "Stroke width",
        )

        Card(
            modifier = Modifier.padding(8.dp),
            backgroundColor = Color.White,
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            border = BorderStroke(strokeState, color),
            onClick = onCardArrowClick // Toggle expand/collapse when card is clicked
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = header,
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                    )
                    IconButton(
                        modifier = Modifier.rotate(rotationState),
                        onClick = onCardArrowClick // Toggle expand/collapse when arrow is clicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            tint = Color.Black,
                            contentDescription = "Drop Down Arrow"
                        )
                    }
                }
                if (isExpanded) {
                    Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color.Gray.copy(alpha = 0.5f))
                    Text(
                        text = description,
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
