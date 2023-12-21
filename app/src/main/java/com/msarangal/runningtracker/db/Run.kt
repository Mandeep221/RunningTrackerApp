package com.msarangal.runningtracker.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class Run(
    var img: Bitmap? = null,
    var timeStamp: Long = 0L, // Date, when the run was
    var averageSpeedInKMH: Float = 0F,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L, // How long the run was
    var caloriesBurned: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}