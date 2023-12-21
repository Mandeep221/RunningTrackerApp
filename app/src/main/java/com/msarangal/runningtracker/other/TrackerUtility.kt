package com.msarangal.runningtracker.other

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object TrackerUtility {

    fun hasLocationPermissions(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // don't need explicit permission for ACCESS_BACKGROUND_LOCATION
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }
}