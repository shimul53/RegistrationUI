package com.example.registrationui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.registrationui.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimeUI(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val images = listOf(
        R.drawable.slider1,
        R.drawable.slider2,
        R.drawable.slider3,
        R.drawable.slider4,
        // Add more images here
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
                        "Prayer Time",
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

                ImageTextRow( image = painterResource(id = R.drawable.sun_image),
                    dayText = "Monday,",
                    dateText = "29 October 2023", timeText = " 05:30pm", nextAzanText ="Next Azan Comes After" , nextAzanTimeText = "00:45:12")

                CustomSlider(imageList = images)

              /*  Image(
                    painter = painterResource(id = R.drawable.prayer_banner),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(top = 16.dp, start = 5.dp, end = 5.dp)
                )*/

                ThreeCardLayout(navController = navController)


            }

        }
    }
}


@Composable
fun ImageTextRow(
    image: Painter,
    dayText: String,
    dateText: String,
    nextAzanText: String,
    nextAzanTimeText: String,
    timeText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Image and text on the left side
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = dayText,
                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = timeText,
                        color = Color(0xff1a71bf),
                        fontSize = 14.sp,fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = dateText,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }

        // Texts on the right side
        Column(

            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start, modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = nextAzanText,
                    fontSize = 14.sp,fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = nextAzanTimeText,
                    color = Color(0xff1a71bf),
                    fontSize = 13.sp, textAlign = TextAlign.Start, fontWeight = FontWeight.SemiBold
                )
            }

        }
    }



}

@Composable
fun ThreeCardLayout(navController: NavHostController) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        // Row with 2 cards aligned horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (modifier = Modifier.weight(1f)){
                SixCardItem(navController = navController)
               /* TasbeehCountCard(
                    imageRes = R.drawable.tasbeeh,
                    title = "Tasbeeh",
                    subtitle = "Counter", onClick = {navController.navigate("tasbeehCount")}

                )*/
                /*Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp).weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (modifier = Modifier.weight(1f)){
                        TasbeehCountCard(
                            imageRes = R.drawable.tasbeeh,
                            title = "Tasbeeh",
                            subtitle = "Counter", onClick = {navController.navigate("tasbeehCount")}

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Column (modifier = Modifier.weight(1f)){
                        PrayerCard(
                            imageRes = R.drawable.prayer_img,
                            title = "Namaz Learning",
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp).weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (modifier = Modifier.weight(1f)){
                        TasbeehCountCard(
                            imageRes = R.drawable.tasbeeh,
                            title = "Tasbeeh",
                            subtitle = "Counter", onClick = {navController.navigate("tasbeehCount")}

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Column (modifier = Modifier.weight(1f)){
                        PrayerCard(
                            imageRes = R.drawable.prayer_img,
                            title = "Namaz Learning",
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp).weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (modifier = Modifier.weight(1f)){
                        TasbeehCountCard(
                            imageRes = R.drawable.tasbeeh,
                            title = "Tasbeeh",
                            subtitle = "Counter", onClick = {navController.navigate("tasbeehCount")}

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Column (modifier = Modifier.weight(1f)){
                        PrayerCard(
                            imageRes = R.drawable.prayer_img,
                            title = "Namaz Learning",
                        )
                    }

                }*/


            }
            Spacer(modifier = Modifier.width(10.dp))
            Column (modifier = Modifier.weight(1f)){
                PrayerTimeCard(
                    imageRes = R.drawable.mosque_img,
                    title = "Prayer Time",

                )
                
            }

        }

    }
}

@Composable
fun TasbeehCountCard(imageRes: Int, title: String, subtitle: String) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                        )
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, fontSize = 21.sp, fontWeight = FontWeight.SemiBold)
            Text(text = subtitle, fontSize = 16.sp)
        }
    }
}

@Composable
fun SixCardItem(navController: NavHostController) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column (modifier = Modifier.weight(1f).padding(10.dp)){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(5.dp).clickable (interactionSource = remember { MutableInteractionSource() },
                        indication = null){ navController.navigate("tasbeehCount") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                                )
                            )
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tasbeeh),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Tasbeeh", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "Counter", fontSize = 10.sp)
                }

                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                                )
                            )
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.prayer_img),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .padding(2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Duah", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                                )
                            )
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hadith_img),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Hadith", fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold)

                }

                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                                )
                            )
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.kiblah_finder),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Kiblah", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "Finder", fontSize = 10.sp)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                                )
                            )
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ramadan_calender),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Ramadan", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "Calendar", fontSize = 10.sp)
                }

                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                                )
                            )
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.namaz_learning_img),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Namaz", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "Leaning", fontSize = 10.sp)
                }
            }
        }


    }
}
@Composable
fun PrayerCard(imageRes: Int, title: String,) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                        )
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

        }
    }
}


@Composable
fun PrayerTimeCard(imageRes: Int, title: String) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            GradientText(
                text = title,
                gradient = Brush.linearGradient(
                    colors = listOf(Color(0xff40A0F5), Color(0xff085BA6))
                ),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)

                )

            Spacer(modifier = Modifier.height(2.dp))
            PrayerTimesLayout()

        }



    }
}

@Composable
fun GradientText(
    text: String,
    gradient: Brush,
    fontSize: TextUnit,
    fontWeight: FontWeight? = null
) {
    Text(
        text = text, fontWeight = FontWeight.SemiBold,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            brush = gradient
        ),
        modifier = Modifier.graphicsLayer(alpha = 0.99f)
    )
}


@Composable
fun PrayerTimesLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        PrayerTimeRow("Fajr", "5:00am", isActive = false)
        PrayerTimeRow("Dhuhr", "1:30pm", isActive = false)
        PrayerTimeRow("Asr", "5:00pm", isActive = false)
        PrayerTimeRow("Maghrib", "7:30pm", isActive = true)
        PrayerTimeRow("Isha", "9:00pm", isActive = false)
        PrayerTimeRow("Jumu'ah", "1:15pm", isActive = false)
    }
}

@Composable
fun PrayerTimeRow(prayerName: String, time: String, isActive: Boolean) {
    val backgroundBrush = if (isActive) {
        Brush.linearGradient(
            colors = listOf(Color(0xff40A0F5), Color(0xff085BA6)) // Gradient colors for active state
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent)
        )
    }
    val cornerRadius = 16.dp
    val textColor = if (isActive) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = backgroundBrush,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = prayerName, fontSize = 14.sp, color = textColor, modifier = Modifier.padding(3.dp))
            Text(text = time, fontSize = 14.sp, color = textColor, modifier = Modifier.padding(3.dp))
        }
    }
}




@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(imageList: List<Int>) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) { page ->
            Image(
                painter = painterResource(id = imageList[page]),
                contentDescription = "Image $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray)
            )
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    backwardIcon: ImageVector = Icons.Default.KeyboardArrowLeft,
    forwardIcon: ImageVector = Icons.Default.KeyboardArrowRight,
    dotsActiveColor: Color = Color.DarkGray,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 10.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 70.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
    imageList: List<Int>
) {
    val pageCount = imageList.size
    // Start at the middle of the "infinite" pager
    val infinitePageCount = Int.MAX_VALUE
    val initialPage = infinitePageCount / 2

    val pagerState = rememberPagerState(initialPage = initialPage)
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            HorizontalPager(
                count = infinitePageCount,
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier.weight(1f)
            ) { page ->
                // Calculate the actual image index based on the current page
                val actualPage = (page - initialPage).floorMod(pageCount)

                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffset

                val scaleFactor = 0.85f + (1f - 0.85f) * (1f - pageOffset.absoluteValue)

                Box(
                    modifier = modifier
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                        .alpha(scaleFactor.coerceIn(0f, 1f))
                        .padding(10.dp)
                        .clip(RoundedCornerShape(imageCornerRadius))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .scale(Scale.FILL)
                            .crossfade(true)
                            .data(imageList[actualPage])
                            .build(),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = imageList[actualPage]),
                        modifier = modifier.height(imageHeight).background(Color.Black)
                    )
                }
            }
        }

        // Dots indicator (optional)
        /*Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { index ->
                val selected = (pagerState.currentPage - initialPage).floorMod(pageCount) == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(dotsSize)
                        .background(
                            color = if (selected) dotsActiveColor else dotsInActiveColor,
                            shape = CircleShape
                        )
                )
            }
        }*/
    }

    // Auto-scroll logic (optional)
    /*LaunchedEffect(pagerState) {
        while (true) {
            delay(3000) // Adjust delay as needed
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }*/
}

// Extension function for modulo operation that handles negative values
fun Int.floorMod(other: Int): Int = ((this % other) + other) % other





