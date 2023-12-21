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
import com.msarangal.runningtracker.other.Constants
import com.msarangal.runningtracker.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.msarangal.runningtracker.other.Constants.REQUEST_CODE_NOTIFICATIONS_PERMISSIONS
import com.msarangal.runningtracker.other.TrackerUtility
import com.msarangal.runningtracker.services.TrackingService
import com.msarangal.runningtracker.ui.composables.TrackingScreen
import com.msarangal.runningtracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class TrackingFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TrackingScreen(onToggleBtnClick = ::onToggleBtnClick)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
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