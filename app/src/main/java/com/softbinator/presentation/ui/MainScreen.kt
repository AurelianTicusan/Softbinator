package com.softbinator.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.softbinator.network.data.Animal
import com.softbinator.presentation.HomeViewModel

@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    onAnimalClicked: (animal: Animal) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val moviePagingItems: LazyPagingItems<Animal> =
            homeViewModel.animalsState.collectAsLazyPagingItems()
        LazyColumn(modifier = Modifier.padding()) {
            items(moviePagingItems.itemCount) { index ->
                val animal = moviePagingItems[index]
                animal?.apply {
                    Text(
                        modifier = Modifier
                            .height(50.dp)
                            .clickable { onAnimalClicked(animal) },
                        text = animal.name,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            moviePagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
//                                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error =
                            moviePagingItems.loadState.refresh as LoadState.Error
                        item {
                            /*ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })*/
                        }
                    }

                    loadState.append is LoadState.Loading -> {
//                                    item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = moviePagingItems.loadState.append as LoadState.Error
                        /*item {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }*/
                    }
                }
            }
        }
    }
}