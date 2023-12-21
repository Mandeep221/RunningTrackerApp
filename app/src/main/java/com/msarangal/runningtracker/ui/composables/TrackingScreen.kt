package com.msarangal.runningtracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrackingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        MapContainerView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        TimerView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.Blue)
        )
    }
}

@Composable
fun MapContainerView(modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(text = "Lets go")
    }
}

@Composable
fun TimerView(modifier: Modifier, showFinishBtn: Boolean = false) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 32.dp),
            text = "00:00:00:00", style = TextStyle(fontSize = 48.sp)
        )
        Button(onClick = {
            if (showFinishBtn) {
                // Do something
            } else {
                // Do something else
            }
        }) {
            Text(text = if (showFinishBtn) "FINISH RUN" else "START")
        }
    }
}

@Preview
@Composable
fun TrackingPreview() {
    TrackingScreen()
}