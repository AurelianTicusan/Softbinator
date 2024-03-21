@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.softbinator.presentation.ui

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.softbinator.R
import com.softbinator.presentation.HomeViewModel
import com.softbinator.presentation.ui.Navigation.Args.ANIMAL_ID
import com.softbinator.presentation.ui.Navigation.Args.ANIMAL_NAME

object Navigation {
    object Args {
        const val ANIMAL_ID = "animal_id"
        const val ANIMAL_NAME = "animal_name"
    }

    const val START_ROUTE = "Home"
    const val ANIMAL_DETAILS_ROUTE = "AnimalDetails"

    sealed class Route(val ordinal: Int, val route: String) {
        data object Start : Route(0, START_ROUTE)
        data object AnimalDetails : Route(1, "$ANIMAL_DETAILS_ROUTE/{$ANIMAL_ID}/{$ANIMAL_NAME}") {
            fun createRoute(animalId: Int, animalName: String) =
                "AnimalDetails/$animalId/$animalName"

            fun createArgumentTypes() = listOf(
                navArgument(ANIMAL_ID) { type = NavType.IntType },
                navArgument(ANIMAL_NAME) { type = NavType.StringType },
            )
        }

        companion object {
            fun findOrdinalByRoute(route: String?): Int {
                return when {
                    route.isNullOrEmpty() -> 0
                    route.startsWith(START_ROUTE) -> 0
                    route.startsWith(ANIMAL_DETAILS_ROUTE) -> 1
                    else -> 0
                }
            }
        }
    }
}

@Composable
fun SoftbinatorAppBar(
    currentScreenName: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = currentScreenName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
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
    homeViewModel: HomeViewModel,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route
    val screenOrdinal = Navigation.Route.findOrdinalByRoute(currentRoute)
    val screenName = when (screenOrdinal) {
        Navigation.Route.Start.ordinal -> Navigation.Route.Start.route
        Navigation.Route.AnimalDetails.ordinal ->
            backStackEntry?.arguments?.run { getString(ANIMAL_NAME) } ?: "Details"

        else -> ""
    }

    Scaffold(
        topBar = {
            SoftbinatorAppBar(
                currentScreenName = screenName,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Navigation.Route.Start.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(
                route = Navigation.Route.Start.route
            ) {
                MainScreen(homeViewModel) {
                    val route = Navigation.Route.AnimalDetails.createRoute(it.id, it.name)
                    navController.navigate(route)
                }
            }
            composable(
                route = Navigation.Route.AnimalDetails.route,
                arguments = Navigation.Route.AnimalDetails.createArgumentTypes()
            ) {
                val animalId = backStackEntry?.arguments?.run { getInt(ANIMAL_ID) } ?: -1
                val animalName = backStackEntry?.arguments?.run { getString(ANIMAL_NAME) } ?: ""
                AnimalsDetailsScreen(id = animalId, animalName = animalName)
            }
        }
    }
}