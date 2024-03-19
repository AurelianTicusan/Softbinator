package com.softbinator.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimalsDetailsScreen(
    animalName: String
) {
    Text(
        text = animalName,
        modifier = Modifier
            .fillMaxSize()
    )
}