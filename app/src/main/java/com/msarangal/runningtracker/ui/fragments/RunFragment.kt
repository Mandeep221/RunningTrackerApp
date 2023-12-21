package com.msarangal.runningtracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.msarangal.runningtracker.R
import com.msarangal.runningtracker.ui.composables.RunScreen
import com.msarangal.runningtracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            RunScreen(onFabClick = ::handleOnFabClick)
        }
    }

    private fun handleOnFabClick() {
        findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
    }
}