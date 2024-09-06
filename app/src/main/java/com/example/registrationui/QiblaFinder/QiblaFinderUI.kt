/*
package com.example.registrationui.QiblaFinder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.health.connect.datatypes.ExerciseRoute
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.example.registrationui.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import okhttp3.internal.wait
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
    val drawable: Drawable = ContextCompat.getDrawable(context, drawableId)!!
    return drawable.toBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun QiblaFinderUI(navController: NavHostController) {
    var direction by remember { mutableStateOf(0f) }
    var permissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val needleBitmap = remember { drawableToBitmap(context, R.drawable.needles).asImageBitmap() }

    RequestLocationPermission(
        onPermissionGranted = {
            permissionGranted = true
        }
    ) {

            GetCurrentLocation { location ->
                direction = calculateQiblaDirection(location.latitude, location.longitude)
            }

            QiblaCompass(direction, compassBgBitmap = needleBitmap)
        }

}

@Composable
fun QiblaCompass(currentDirection: Float, compassBgBitmap: ImageBitmap) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Calculate the center and radius of the compass
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

        // Optional: Draw additional elements like compass needle or markers here
    }
}

fun calculateQiblaDirection(latitude: Double, longitude: Double): Float {
    val kaabaLatitude = 21.4225
    val kaabaLongitude = 39.8262

    val latDifference = Math.toRadians(kaabaLatitude - latitude)
    val lonDifference = Math.toRadians(kaabaLongitude - longitude)

    val y = sin(lonDifference) * cos(Math.toRadians(kaabaLatitude))
    val x = cos(Math.toRadians(latitude)) * sin(Math.toRadians(kaabaLatitude)) -
            sin(Math.toRadians(latitude)) * cos(Math.toRadians(kaabaLatitude)) * cos(lonDifference)
    val bearing = Math.toDegrees(atan2(y, x))

    return (bearing + 360).toFloat() % 360
}

@Composable
fun GetCurrentLocation(onLocationFound: (android.location.Location) -> Unit) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let { onLocationFound(it) }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    when {
        permissionState.permission.toBoolean() -> {
            // Permission granted
            onPermissionGranted()
            content()
        }
        permissionState.status.shouldShowRationale -> {
            // Show rationale to the user before requesting permission again
            Column {
                Text("Location permission is needed to show the Qibla direction.")
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Grant Permission")
                }
            }
        }
        else -> {
            // Request permission
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}*/
