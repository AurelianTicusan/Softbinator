package com.softbinator.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.softbinator.R
import com.softbinator.presentation.HomeViewModel
import com.softbinator.presentation.state.AnimalItem
import com.softbinator.presentation.state.PhotoItem
import com.softbinator.presentation.ui.theme.AdoptableColor


@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    onAnimalClicked: (animal: AnimalItem) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val moviePagingItems: LazyPagingItems<AnimalItem> =
            homeViewModel.animalsState.collectAsLazyPagingItems()
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            contentPadding = PaddingValues(12.5.dp),
            verticalArrangement = Arrangement.spacedBy(12.5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(moviePagingItems.itemCount) { index ->
                val animal = moviePagingItems[index]
                animal?.apply {
                    AnimalTile(
                        animal = animal,
                        onAnimalClicked = onAnimalClicked
                    )
                }
            }

            moviePagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .height(screenHeight)
                            ) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = moviePagingItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessageRefresh(
                                message = error.error.localizedMessage ?: "Unknown Error"
                            ) { retry() }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = moviePagingItems.loadState.append as LoadState.Error
                        item {
                            ErrorMessageAppend(
                                message = error.error.localizedMessage ?: "Unknown Error"
                            ) { retry() }
                        }
                    }

                    loadState.refresh is LoadState.NotLoading -> {
                        if (itemCount == 0) {
                            item {
                                Box(modifier = Modifier.height(screenHeight)) {
                                    NoItemsHomeScreen { moviePagingItems.refresh() }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoItemsHomeScreen(onClickRefresh: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_empty_items),
            contentDescription = null
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.home_screen_no_items_label),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Button(onClick = onClickRefresh) {
                Text(text = stringResource(R.string.refresh_label))
            }
        }
    }
}

@Composable
fun ErrorMessageRefresh(message: String, onClickRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = Color.Red,
                text = stringResource(R.string.error_retrieving_animals, message),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Button(onClick = onClickRetry) {
                Text(text = stringResource(R.string.retry_label))
            }
        }
    }
}

@Composable
fun ErrorMessageAppend(message: String, onClickRetry: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_error), contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Red)
        )
        Spacer(modifier = Modifier.width(12.5.dp))
        Button(onClick = onClickRetry) {
            Text(
                text = stringResource(R.string.retry_label),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AnimalTile(
    animal: AnimalItem,
    onAnimalClicked: (animal: AnimalItem) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .height(150.dp)
            .clickable { onAnimalClicked(animal) }
    ) {
        val url = animal.photos.firstOrNull()?.small

        Image(
            painter = rememberAsyncImagePainter(
                model = url,
                placeholder = painterResource(id = R.drawable.fallback_animal),
                fallback = painterResource(id = R.drawable.fallback_animal),
                error = painterResource(id = R.drawable.fallback_animal)
            ), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxHeight()
                .aspectRatio(1.0f)
        )
        Column(
            modifier = Modifier.padding(12.5.dp)
        ) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = animal.name,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                modifier = Modifier,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${animal.species} \u2022 ${animal.gender}",
                modifier = Modifier
            )

            val annotatedString = buildAnnotatedString {
                append("Status: ")
                withStyle(style = SpanStyle(AdoptableColor)) {
                    append(animal.status)
                }
            }

            Text(
                text = annotatedString,
                modifier = Modifier,
            )

            Text(
                text = "Age: ${animal.age}",
                modifier = Modifier
            )

            Text(
                text = animal.publishedAt.substring(0, 10),
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
private fun AnimalTilePreview() {
    val animal = AnimalItem(
        id = -1,
        photos = arrayListOf(
            PhotoItem(
                small =
                "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/71098959/4/?bust=1710934497&width=100",
                medium = null,
                large = null,
                full = null
            )
        ),
        name = "Pandy",
        status = "adoptable",
        gender = "Female",
        species = "Red Panda",
        age = "Adult",
        description = null,
        breeds = null,
        publishedAt = "2024-03-20T11:35:21+0000"
    )
    AnimalTile(animal = animal) {

    }
}