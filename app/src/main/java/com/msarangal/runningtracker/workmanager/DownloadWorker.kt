package com.msarangal.runningtracker.workmanager

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.msarangal.runningtracker.R
import com.msarangal.runningtracker.network.FileApi
import com.msarangal.runningtracker.other.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random

class DownloadWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        startForegroundService()
        delay(5000L)

        val response = FileApi.instance.downloadImage() // Rest Api call to download the image

        response.body()?.let { body ->
            return withContext(Dispatchers.IO) {
                // Coroutine started because there is an IO operation
                // to SAVE the downloaded image to local file system
                val file = File(context.cacheDir, "image.jpg")
                val outputStream = FileOutputStream(file)
                outputStream.use { stream ->
                    try {
                        stream.write(body.bytes())
                    } catch (e: IOException) {
                        return@withContext Result.failure(
                            workDataOf(
                                Constants.ERROR_MSG to e.localizedMessage
                            )
                        )
                    }
                }
                // Return Result.Success because the file was successfully saved in the file system
                Result.success(
                    workDataOf(
                        Constants.IMAGE_URI to file.toUri().toString()
                    )
                )
            }
        }
        if (response.isSuccessful.not()) { // Rest Api call failed.
            if (response.code().toString().startsWith("5")) {
                return Result.retry() // Retry request
            }
            return Result.failure(
                workDataOf(
                    Constants.ERROR_MSG to "Network Error"
                )
            )
        }
        return Result.failure(
            workDataOf(
                Constants.ERROR_MSG to "Unknown Error"
            )
        )
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "download channel")
                    .setSmallIcon(R.drawable.ic_run)
                    .setContentText("Downloading...")
                    .setContentTitle("Download in Progress")
                    .build()
            )
        )
    }
}