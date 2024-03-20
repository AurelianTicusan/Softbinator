package com.softbinator.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.softbinator.presentation.AnimalDetailsViewModel
import com.softbinator.presentation.HomeViewModel
import com.softbinator.presentation.ui.theme.SoftbinatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val animalDetailsViewModel: AnimalDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoftbinatorTheme {
                SoftbinatorApp(
                    homeViewModel = homeViewModel,
                    animalDetailsViewModel = animalDetailsViewModel
                )
            }
        }
    }
}