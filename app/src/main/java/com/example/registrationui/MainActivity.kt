package com.example.registrationui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.registrationui.QiblaFinder.MainViewModel
import com.example.registrationui.QiblaFinder.calculateQiblaDirection
import com.hazrat.qiblafinder.service.CompassSensorManager
import com.hazrat.qiblafinder.service.LocationManager
import com.hazrat.qiblafinder.service.PermissionsManager
import dagger.hilt.android.AndroidEntryPoint
import navigation.NavController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var compassSensorManager: CompassSensorManager

    private lateinit var permissionsManager: PermissionsManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel by viewModels<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize permissions manager and set the callback
        permissionsManager = PermissionsManager(this)
        permissionsManager.onPermissionGranted = {
            getLastKnownLocation()
        }
        checkLocationPermission()  // Ensure this is called to check and request permissions

        // Ensure that the locationManager is accessed after it has been injected
        locationManager.onLocationReceived = { location ->
            val qiblaDirection = calculateQiblaDirection(location.latitude, location.longitude).toFloat()
            Log.d("MainActivity Qibla", "New Qibla Direction: $qiblaDirection")
            viewModel.updateQiblaDirection(qiblaDirection)
            viewModel.fetchPrayerTimes(latitude = location.latitude, longitude = location.longitude)
        }

        compassSensorManager.onDirectionChanged = { direction ->
            Log.d("MainActivity Current", "New Current Direction: $direction")
            viewModel.updateCurrentDirection(direction)
        }

        setContent {
            NavController(viewModel = viewModel)
        }

        // Register listeners after all initializations
        compassSensorManager.registerListeners()
    }

    // Method to check location permissions and request if not granted
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        getLastKnownLocation()
    }

    // Method to get the last known location and pass it to the ViewModel
    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude

                    // Fetch prayer times using the fetched latitude and longitude
                    viewModel.fetchPrayerTimes(latitude = latitude, longitude = longitude)
                } ?: run {
                    // Handle case where location is null
                    viewModel.fetchPrayerTimes(latitude = 51.7587, longitude = -1.2539)  // Default location
                }
            }
            .addOnFailureListener {
                // Handle failure to get location
                viewModel.fetchPrayerTimes(latitude = 51.7587, longitude = -1.2539)  // Default location
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array< String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getLastKnownLocation()
            } else {
                // Permission denied
                // Handle the case where permission is denied
                Log.e("MainActivity", "Location permission denied")
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
