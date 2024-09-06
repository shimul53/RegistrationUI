package com.example.registrationui.QiblaFinder


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.example.registrationui.ui.CustomSlider
import com.example.registrationui.ui.ImageTextRow
import com.example.registrationui.ui.ThreeCardLayout
import com.example.registrationui.ui.theme.Hidaya
import com.hazrat.qiblafinder.util.vibrateDevice
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QiblaScreen(
    modifier: Modifier = Modifier,
    qiblaDirection: Float,
    currentDirection: Float,
    navController: NavHostController
) {


    val context = LocalContext.current

    val compassBgBitmap =
        remember { drawableToBitmap(context, R.drawable.compass3).asImageBitmap() }
    val qiblaIconBitmap =
        remember { drawableToBitmap(context, R.drawable.qiblaiconpoint).asImageBitmap() }
    val needleBitmap = remember { drawableToBitmap(context, R.drawable.needles).asImageBitmap() }
    val goldQaba = remember { drawableToBitmap(context, R.drawable.goldqaba).asImageBitmap() }

    val minTolerance = 3f // Adjusted tolerance range
    val maxTolerance = 3.5f // Adjusted tolerance range

    val directionDifference = qiblaDirection - currentDirection
    val normalizedDifference = (directionDifference + 360) % 360

    val isFacingQibla = (
            (normalizedDifference in 0.0..maxTolerance.toDouble()) ||
                    (normalizedDifference >= 360 - minTolerance && normalizedDifference <= 360)
            )

    var hasVibrated by remember { mutableStateOf(false) }

    // Vibrate when facing Qibla and not already vibrated
    if (isFacingQibla && !hasVibrated) {
        vibrateDevice(context, 200)
        hasVibrated = true // Set to true to prevent continuous vibration
    } else if (!isFacingQibla) {
        hasVibrated = false // Reset when not facing Qibla
    }

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
                        "Qibla Location",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Qibla: ${qiblaDirection.toInt()}°",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Hidaya
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (isFacingQibla) {
                        Icon(
                            modifier = Modifier.size(60.dp),
                            painter = painterResource(id = R.drawable.goldqaba),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.arrowup),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .padding(10.dp)
                    ) {
                        // Canvas for compass
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val compassCenter = Offset(size.width / 2, size.height / 2)
                            val compassRadius = size.minDimension / 2.5f

                            // Rotate the entire compass background image
                            rotate(degrees = -currentDirection, pivot = compassCenter) {
                                drawImage(
                                    image = compassBgBitmap,
                                    topLeft = Offset(
                                        compassCenter.x - compassBgBitmap.width / 2,
                                        compassCenter.y - compassBgBitmap.height / 2
                                    )
                                )
                            }
                            // Draw the Qibla direction needle
                            rotate(degrees = if(qiblaDirection.toInt() != 277) qiblaDirection - currentDirection else qiblaDirection - currentDirection-5, pivot = compassCenter) {
                                val needleStartY = compassCenter.y - needleBitmap.height / 1.15f
                                drawImage(
                                    image = needleBitmap,
                                    topLeft = Offset(
                                        compassCenter.x - needleBitmap.width / 2f,
                                        needleStartY
                                    )
                                )

                                // Draw the Qibla icon
                                if (!isFacingQibla) {
                                    drawImage(
                                        image = qiblaIconBitmap,
                                        topLeft = Offset(
                                            compassCenter.x - qiblaIconBitmap.width / 2,
                                            compassCenter.y - compassRadius - qiblaIconBitmap.height / 1.0f
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }



        }
    }


}



