package com.msarangal.runningtracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.LightGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItemView(
                modifier = Modifier
                    .weight(1f),
                text = "00:00:00",
                subText = "Total time"
            )
            StatItemView(
                modifier = Modifier
                    .weight(1f),
                text = "0 Km",
                subText = "Total Distance"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.LightGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItemView(
                modifier = Modifier.weight(1f),
                text = "0 Kcal",
                subText = "Total Calories Burned"
            )
            StatItemView(
                modifier = Modifier.weight(1f),
                text = "0 Km/h",
                subText = "Average Speed"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
                .weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Place for chart")
        }
    }
}

@Composable
fun StatItemView(
    modifier: Modifier = Modifier,
    text: String,
    subText: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, fontSize = 32.sp)
        Text(text = subText)
    }
}

@Preview
@Composable
fun StatisticsPreview() {
    StatisticsScreen()
}