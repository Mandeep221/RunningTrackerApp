package com.msarangal.runningtracker.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.msarangal.runningtracker.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.msarangal.runningtracker.services.TrackingService
import com.msarangal.runningtracker.ui.composables.TrackingScreen
import com.msarangal.runningtracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment() {

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

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    private fun onToggleBtnClick() {
        sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
    }
}