package com.softbinator.data.mapper

import com.softbinator.data.network.entities.BreedRemote
import com.softbinator.domain.model.Breed

fun BreedRemote.toBreed(): Breed = Breed(
    primary = primary,
    secondary = secondary,
    mixed = mixed,
    unknown = unknown
)