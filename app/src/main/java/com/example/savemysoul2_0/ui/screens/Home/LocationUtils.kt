package com.example.savemysoul2_0.ui.screens.Home

import android.Manifest
import android.content.Context
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class LocationUtils @Inject constructor(
    private val context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationCallback: LocationCallback? = null

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationMode: Int = try {
            Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.LOCATION_MODE
            )
        } catch (e: Settings.SettingNotFoundException) {
            Settings.Secure.LOCATION_MODE_OFF
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF
    }

    fun getCurrentLocation(
        onLocationReceived: (android.location.Location?) -> Unit,
        onPermissionDenied: () -> Unit,
        onLocationDisabled: () -> Unit
    ) {
        if (!checkLocationPermission()) {
            onPermissionDenied()
            return
        }

        if (!isLocationEnabled()) {
            onLocationDisabled()
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {

                onLocationReceived(location)
            } else {
                startLocationUpdates(onLocationReceived)
            }
        }
    }

    fun startLocationUpdates(
        onLocationReceived: (android.location.Location?) -> Unit
    ) {
        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.locations.firstOrNull()?.let { location ->
                    onLocationReceived(location)
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback!!, Looper.getMainLooper())
        } catch (exception: SecurityException) {
            Log.e("error", exception.printStackTrace().toString())
        }
    }

    private fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }
}