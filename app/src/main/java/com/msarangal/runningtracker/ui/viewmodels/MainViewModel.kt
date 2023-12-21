package com.msarangal.runningtracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.msarangal.runningtracker.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository
): ViewModel() {
}