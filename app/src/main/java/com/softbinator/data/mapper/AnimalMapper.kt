package com.softbinator.data.mapper

import com.softbinator.data.network.entities.AnimalRemote
import com.softbinator.domain.model.Animal

fun AnimalRemote.toAnimal(): Animal = Animal(
    id = id ?: -1,
    name = name.orEmpty(),
    gender = gender.orEmpty(),
    status = status.orEmpty(),
    species = species.orEmpty(),
    age = age.orEmpty(),
    photos = photos?.map { it.toPhoto() }.orEmpty(),
    description = description.orEmpty(),
    breeds = breeds?.toBreed(),
    publishedAt = publishedAt.orEmpty()
)