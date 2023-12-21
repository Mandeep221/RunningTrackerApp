package com.msarangal.runningtracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.msarangal.runningtracker.R
import com.msarangal.runningtracker.ui.composables.SetupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            SetupScreen(onClickContinue = ::handleOnClickContinue)
        }
    }

    private fun handleOnClickContinue() {
        findNavController().navigate(R.id.action_setupFragment_to_runFragment)
    }
}