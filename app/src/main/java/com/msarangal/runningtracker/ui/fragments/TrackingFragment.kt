package com.msarangal.runningtracker.ui.fragments

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.model.PolylineOptions
import com.msarangal.runningtracker.other.Constants.ACTION_PAUSE_SERVICE
import com.msarangal.runningtracker.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.msarangal.runningtracker.other.Constants.POLYLINE_COLOR
import com.msarangal.runningtracker.other.Constants.POLYLINE_WIDTH
import com.msarangal.runningtracker.other.Constants.REQUEST_CODE_NOTIFICATIONS_PERMISSIONS
import com.msarangal.runningtracker.other.TrackerUtility
import com.msarangal.runningtracker.services.PolyLine
import com.msarangal.runningtracker.services.TrackingService
import com.msarangal.runningtracker.ui.composables.TrackingScreen
import com.msarangal.runningtracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class TrackingFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val mainViewModel: MainViewModel by viewModels()

    private var isTracking: Boolean = false
    private var pathPoints = mutableListOf<PolyLine>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TrackingScreen(
                onToggleBtnClick = ::toggleRun
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner) {
            updateTracking(it)
        }

        TrackingService.pathPoints.observe(viewLifecycleOwner) {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        }
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            // change button text to "Start"
            // make the finish run visible
        } else {
            // change the button text to "Stop"
            // hide finish run
        }
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            // animate camera, MAP_ZOOM constant is used here.
        }
    }

    private fun addAllPolylines() {
        pathPoints.forEach { polyline ->
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)

            // add each polyline to the map
        }
    }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val secondLastLatLong = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLong = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(secondLastLatLong)
                .add(lastLatLong)
        }
        // polyline add to map
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissions() {
        if (TrackerUtility.hasNotificationPermissions(requireContext())) {
            return
        }
        EasyPermissions.requestPermissions(
            this,
            "You need to provide notification permissions to use this app",
            REQUEST_CODE_NOTIFICATIONS_PERMISSIONS,
            Manifest.permission.POST_NOTIFICATIONS
        )
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    private fun onToggleBtnClick() {
        sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}