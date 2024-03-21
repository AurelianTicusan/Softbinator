package com.softbinator.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.softbinator.R
import com.softbinator.presentation.AnimalDetailsViewModel
import com.softbinator.presentation.state.AnimalItem
import com.softbinator.presentation.state.UiState

@Composable
fun AnimalsDetailsScreen(
    id: Int,
    animalName: String,
    animalDetailsViewModel: AnimalDetailsViewModel = hiltViewModel()
) {
    animalDetailsViewModel.initialize(id)

    var isErrorTriggered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            modifier = Modifier
                .padding(15.dp)
                .size(35.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    isErrorTriggered = isErrorTriggered.not()
                },
            painter = painterResource(id = if (isErrorTriggered) R.drawable.ic_error_pressed else R.drawable.ic_error_none),
            contentDescription = null
        )
        when (val uiState = animalDetailsViewModel.animalsState.collectAsState().value) {
            is UiState.Loading -> AnimalDetailsScreenLoading()

            is UiState.Loaded -> {
                when (isErrorTriggered) {
                    true -> {
                        AnimalDetailsScreenError(
                            "Cannot retrieve data from server",
                            id,
                            animalName
                        )
                    }

                    else -> AnimalsDetailsScreenSuccess(uiState.data)
                }
            }

            is UiState.Error -> AnimalDetailsScreenError(uiState.error, id, animalName)
        }
    }
}

@Composable
fun AnimalDetailsScreenLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimalsDetailsScreenSuccess(animal = AnimalItem.EMPTY)
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun AnimalDetailsScreenError(error: String?, id: Int, animalName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimalsDetailsScreenSuccess(animal = AnimalItem.EMPTY)
        Text(
            color = Color.Red,
            text = stringResource(
                R.string.details_screen_error_message,
                animalName,
                id,
                error.orEmpty()
            ),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AnimalsDetailsScreenSuccess(
    animal: AnimalItem
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val url = animal.photos.firstOrNull()?.full
        val (image, detailsColumn) = createRefs()
        Image(
            painter = rememberAsyncImagePainter(
                model = url,
                placeholder = painterResource(id = R.drawable.fallback_animal),
                fallback = painterResource(id = R.drawable.fallback_animal),
                error = painterResource(id = R.drawable.fallback_animal)
            ), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    start.linkTo(parent.start, 12.dp)
                    end.linkTo(parent.end, 12.dp)
                    top.linkTo(parent.top, 8.dp)
                }
        )
        Column(modifier = Modifier
            .constrainAs(detailsColumn) {
                end.linkTo(parent.end, 24.dp)
                top.linkTo(image.bottom, 20.dp)
                start.linkTo(parent.start, 24.dp)
                width = Dimension.fillToConstraints
            }) {
            AnimalDetailsRow(
                label = stringResource(R.string.animal_details_name_label),
                value = animal.name
            )
            AnimalDetailsRow(
                label = stringResource(R.string.animal_details_species_label),
                value = animal.species
            )
            AnimalDetailsRow(
                label = stringResource(R.string.animal_details_gender_label),
                value = animal.gender
            )
            if (animal.breeds?.primary != null) {
                AnimalDetailsRow(
                    label = stringResource(R.string.animal_details_breed_label),
                    value = animal.breeds.primary
                )
            }
            AnimalDetailsRow(
                label = stringResource(R.string.animal_details_age_label),
                value = animal.age
            )
            AnimalDetailsRow(
                label = stringResource(R.string.animal_details_status_label),
                value = animal.status
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.5.dp)
            )
            if (animal.description != null) {
                Text(text = animal.description)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.5.dp)
            )
        }
    }
}

@Composable
fun AnimalDetailsRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.5.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = label,
                fontStyle = FontStyle.Italic,
                fontSize = 22.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.5.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.DarkGray)
        )
    }
}