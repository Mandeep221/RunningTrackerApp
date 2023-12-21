package com.msarangal.runningtracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.msarangal.runningtracker.db.RunDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    val runDao: RunDao
) : ViewModel() {
}