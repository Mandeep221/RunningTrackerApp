package com.msarangal.runningtracker.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.msarangal.runningtracker.other.Constants.ACTION_PAUSE_SERVICE
import com.msarangal.runningtracker.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.msarangal.runningtracker.other.Constants.ACTION_STOP_SERVICE
import timber.log.Timber

class TrackingService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    Timber.d("Started or Resumed Service")
                }

                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused Service")
                }

                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped Service")
                }

                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}
