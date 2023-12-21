package com.msarangal.runningtracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 24.dp, horizontal = 24.dp)
                        .fillMaxWidth(),
                    text = "Welcome, Please enter your name and weight",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                TextField(value = "", onValueChange = {}, label = {
                    Text(
                        text = "Enter Name"
                    )
                })
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(
                            text = "Enter Weight"
                        )
                    }, supportingText = {
                        Text(text = "KG")
                    })
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Blue)
                    .padding(16.dp),
                text = "Continue",
                color = Color.White,
                textAlign = TextAlign.End,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun SetupPreview() {
    SetupView()
}