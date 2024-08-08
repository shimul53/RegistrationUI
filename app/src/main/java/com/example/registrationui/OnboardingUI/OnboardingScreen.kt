package com.example.registrationui.OnboardingUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.registrationui.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen(navController: NavController) {

    val pagerState = rememberPagerState()
    val list = getList()

    val isNextVisible = remember { derivedStateOf { pagerState.currentPage != list.size - 1 } }

    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarColors( containerColor = Color.White, actionIconContentColor = Color.Black, navigationIconContentColor = Color.Black, scrolledContainerColor = Color.Black, titleContentColor = Color.Black ),
                title = { Text("Sign Up", color = Color.Black, fontSize = 16.sp) },

                navigationIcon = {
                    IconButton(onClick = { scope.launch {

                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        } else {
                            navController.navigate("login")
                        }

                        // navController.navigate("anotherScreen")

                    } }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.75f)
            ) {
                HorizontalPager(
                    state = pagerState,
                    verticalAlignment = Alignment.CenterVertically,
                    count = list.size
                ) { currentPage ->

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AsyncImage(
                            model = list[currentPage].res,
                            contentDescription = null, modifier = Modifier
                                .height(380.dp)
                                .width(300.dp)
                        )
                        Text(
                            text = list[currentPage].title,
                            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,
                            textAlign = TextAlign.Center,

                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)
                        )

                        Text(
                            text = list[currentPage].description,
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth(.6f)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState, modifier = Modifier
                    .padding(vertical = 26.dp),
                activeColor = Color(0xFF014C8F),
                inactiveColor = Color(0xFFF8AD3C)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Button(onClick = {
                    scope.launch {
                        if (isNextVisible.value) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navController.navigate("signup")
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF014C8F), // Background color
                    contentColor = Color.White // Text color f8ad3c
                )) {
                    //Text(text = if (isNextVisible.value) "Get Started" else "Finish")
                    Text(text = "Get Started")
                }
            }
        }
    }
}

fun getList(): List<HorizontalPagerContent> {
    return listOf(
        HorizontalPagerContent(
            "Welcome to MasterPay.",
            R.drawable.masterpay_onboard,
            "Your Best Money Transfer Partner."
        ),
        HorizontalPagerContent(
            "Saving Your Money",
            R.drawable.savings_onboard,
            "Track the progress of your savings and start a habit of saving with MasterPay."
        ),
        HorizontalPagerContent(
            "Bills Payment Made Easy",
            R.drawable.credit_card_onboard,
            "Pay monthly or daily bills at home in a site of MasterPay."
        ),
        /*HorizontalPagerContent(
            "Zero Fees",
            R.drawable.fourth,
            "Bank your life,We create something new you have never seen before"
        )*/
    )
}


