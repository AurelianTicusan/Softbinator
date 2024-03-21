package com.softbinator.presentation.mapper

import com.softbinator.domain.model.Animal
import com.softbinator.presentation.state.AnimalItem

fun Animal.toAnimalItem(): AnimalItem = AnimalItem(
    id = id,
    name = name,
    gender = gender,
    status = status,
    species = species,
    age = age,
    photos = photos.map { it.toPhotoItem() },
    description = description,
    breeds = breeds,
    publishedAt = publishedAt
)