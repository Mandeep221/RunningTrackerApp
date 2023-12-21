package com.msarangal.runningtracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RunScreen(onFabClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 8.dp),
                    text = "Sort by:",
                    style = TextStyle(textAlign = TextAlign.End)
                )
                SpinnerView(modifier = Modifier.weight(1f))
            }
            LazyColumn(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), content = {
                val runs = listOf("Run1", "Run2", "Run3", "Run4", "Run5")
                runs.forEach {
                    item { Text(text = it) }
                }
            })

            FABView(
                modifier = Modifier.padding(bottom = 92.dp, end = 40.dp)
            ) {
                onFabClick.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerView(modifier: Modifier) {

    val spinnerItems = listOf("item1", "item2", "item3")

    val expandedState = remember {
        mutableStateOf(false)
    }

    val selectedOption = remember {
        mutableStateOf(spinnerItems[0])
    }
    ExposedDropdownMenuBox(
        expanded = expandedState.value,
        onExpandedChange = {
            expandedState.value = !expandedState.value
        },
        modifier = modifier
    ) {

        TextField(
            value = selectedOption.value,
            onValueChange = {

            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value)
            },
            readOnly = true,
            textStyle = TextStyle.Default.copy(fontSize = 18.sp),
            modifier = Modifier.menuAnchor() // missing ingredient.
        )

        ExposedDropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false }) {

            spinnerItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item, fontSize = 18.sp)
                    }, onClick = {
                        selectedOption.value = item
                        expandedState.value = false
                    })
            }

        }

    }
}

@Composable
fun FABView(
    modifier: Modifier,
    onFabClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onFabClick() },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Preview
@Composable
fun RunPreview() {
    RunScreen {}
}