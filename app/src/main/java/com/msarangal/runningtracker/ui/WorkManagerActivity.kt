package com.msarangal.runningtracker.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import coil.compose.rememberImagePainter
import com.msarangal.runningtracker.other.Constants
import com.msarangal.runningtracker.ui.theme.RunningTrackerTheme
import com.msarangal.runningtracker.workmanager.ColorFilterWorker
import com.msarangal.runningtracker.workmanager.DownloadWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkManagerActivity : AppCompatActivity() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        val colorFilterRequest = OneTimeWorkRequestBuilder<ColorFilterWorker>()
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        setContent {
            RunningTrackerTheme {
                // WorkInfos - New Concept
                val workInfos = workManager
                    .getWorkInfosForUniqueWorkLiveData("download")
                    .observeAsState()
                    .value

                val downloadInfo = remember(key1 = workInfos) {
                    workInfos?.find { it.id == downloadRequest.id }
                }

                val colorFilterInfo = remember(key1 = workInfos) {
                    workInfos?.find { it.id == colorFilterRequest.id }
                }

                val imageUri by derivedStateOf {
                    val downloadUri =
                        downloadInfo?.outputData?.getString(Constants.IMAGE_URI)?.toUri()
                    val filterUri =
                        colorFilterInfo?.outputData?.getString(Constants.FILTER_URI)?.toUri()

                    filterUri ?: downloadUri
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    imageUri?.let { uri ->
                        Image(
                            painter = rememberImagePainter(data = uri),
                            contentDescription = "Image",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Button(onClick = {
                        workManager
                            .beginUniqueWork(
                                "download",
                                ExistingWorkPolicy.KEEP,
                                downloadRequest
                            )
                            .then(colorFilterRequest)
                            .enqueue()
                    }, enabled = downloadInfo?.state != WorkInfo.State.RUNNING) {
                        Text(text = "Start Download")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    when (downloadInfo?.state) {
                        WorkInfo.State.RUNNING -> Text(text = "Downloading...")
                        WorkInfo.State.SUCCEEDED -> Text(text = "Download Succeeded")
                        WorkInfo.State.FAILED -> Text(text = "Download Failed")
                        WorkInfo.State.CANCELLED -> Text(text = "Download Cancelled")
                        WorkInfo.State.ENQUEUED -> Text(text = "Download Enqueued")
                        WorkInfo.State.BLOCKED -> Text(text = "Download Blocked")
                        else -> {}
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    when (colorFilterInfo?.state) {
                        WorkInfo.State.RUNNING -> Text(text = "Applying Filter...")
                        WorkInfo.State.SUCCEEDED -> Text(text = "Filter Applied")
                        WorkInfo.State.FAILED -> Text(text = "Applying Filter Failed")
                        WorkInfo.State.CANCELLED -> Text(text = "Applying Filter Cancelled")
                        WorkInfo.State.ENQUEUED -> Text(text = "Applying Filter Enqueued")
                        WorkInfo.State.BLOCKED -> Text(text = "Applying Filter Blocked")
                        else -> {}
                    }
                }
            }
        }
    }
}