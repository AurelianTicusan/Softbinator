@file:OptIn(ExperimentalMaterial3Api::class)

package com.softbinator.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.softbinator.R
import com.softbinator.presentation.HomeViewModel

enum class SoftbinatorScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    AnimalDetails(title = R.string.animals_details),
}

@Composable
fun SoftbinatorAppBar(
    currentScreen: SoftbinatorScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun SoftbinatorApp(
    viewModel: HomeViewModel,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = SoftbinatorScreen.valueOf(
        backStackEntry?.destination?.route ?: SoftbinatorScreen.Start.name
    )

    Scaffold(
        topBar = {
            SoftbinatorAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SoftbinatorScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = SoftbinatorScreen.Start.name) {
                MainScreen(homeViewModel = viewModel) {
                    backStackEntry?.arguments?.apply {
                        putString("animal_name", it.name)
                    }
                    navController.navigate(SoftbinatorScreen.AnimalDetails.name)
                }
            }
            composable(route = SoftbinatorScreen.AnimalDetails.name) {
                val animalName = backStackEntry?.arguments?.run {
                    getString("animal_name")
                } ?: "Unknown"
                AnimalsDetailsScreen(animalName = animalName)
            }
        }
    }
}