package com.softbinator.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.softbinator.R
import com.softbinator.presentation.AnimalDetailsViewModel

@Composable
fun AnimalsDetailsScreen(
    id: Int,
    animalDetailsViewModel: AnimalDetailsViewModel
) {
    animalDetailsViewModel.initialize(id)
    val state = animalDetailsViewModel.animalsState.collectAsState()
    val animal = state.value

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
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
            AnimalDetailsRow(label = "Name ", value = animal.name)
            AnimalDetailsRow(label = "Species ", value = animal.species)
            AnimalDetailsRow(label = "Gender ", value = animal.gender)
            AnimalDetailsRow(label = "Breed ", value = animal.breeds.primary)
            AnimalDetailsRow(label = "Age ", value = animal.age)
            AnimalDetailsRow(label = "Status ", value = animal.status)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.5.dp)
            )
            Text(text = animal.description)
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
                fontSize = 24.sp
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