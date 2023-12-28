package com.msarangal.runningtracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.msarangal.runningtracker.services.PolyLines
import com.msarangal.runningtracker.services.TrackingService

@Composable
fun TrackingScreen(
    onToggleBtnClick: () -> Unit
) {
    val isTracking = TrackingService.isTracking.observeAsState().value
    val pathPoints = TrackingService.pathPoints.observeAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        MapContainerView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pathPoints = pathPoints
        )
        TimerView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.Blue),
            onToggleBtnClick = onToggleBtnClick,
            isTracking = isTracking ?: false
        )
    }
}

@Composable
fun MapContainerView(modifier: Modifier, pathPoints: PolyLines?) {
    var secondLastPosition = LatLng(43.61141, -79.70055)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(secondLastPosition, 15f)
    }

    // use this to animate the Camera to user's postion
    LaunchedEffect(key1 = true) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(
                CameraPosition(secondLastPosition, 15f, 0f, 0f)
            ),
            durationMs = 1000
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            pathPoints?.let { polylines ->
                if (polylines.isNotEmpty() && polylines.last().size > 1) {
                    secondLastPosition = polylines.last()[polylines.last().size - 2]
                    val lastPathPoint = polylines.last().last()

                    Polyline(points = listOf(secondLastPosition, lastPathPoint), color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun TimerView(
    modifier: Modifier,
    isTracking: Boolean = true,
    onToggleBtnClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 32.dp),
            text = "00:00:00:00", style = TextStyle(fontSize = 48.sp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(42.dp)) {
            Button(onClick = {
                onToggleBtnClick.invoke()
            }) {
                Text(text = if (isTracking) "STOP" else "START")
            }

            if (isTracking) {
                Button(onClick = {

                }) {
                    Text(text = "Finish Run")
                }
            }
        }
    }
}

@Preview
@Composable
fun TrackingPreview() {
    TrackingScreen {}
}

/**
 *

Polyline(
points = listOf(
LatLng(44.811058, 20.4617586),
LatLng(44.811058, 20.4627586),
LatLng(44.810058, 20.4627586),
LatLng(44.809058, 20.4627586),
LatLng(44.809058, 20.4617586)
)
,color = Color.Magenta
)


 * Mississauga = 43.595310, -79.640579
 *
//            Marker(
//                state = MarkerState(position = singapore),
//                title = "Singapore",
//                snippet = "Marker in Singapore"
//            )


//                if (polylines.isNotEmpty() && polylines.last().size > 1) {
//                    val secondLastLatLong = polylines.last()[polylines.last().size - 2]
//                    val lastLatLong = polylines.last().last()
//
//                    Polyline(
//                        points = listOf(secondLastLatLong, lastLatLong),
//                        color = Color.Red
//                    )
//                }
 */