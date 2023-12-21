package com.msarangal.runningtracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.msarangal.runningtracker.ui.composables.TrackingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TrackingScreen()
        }
    }
}