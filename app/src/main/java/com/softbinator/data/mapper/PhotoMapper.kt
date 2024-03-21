package com.softbinator.data.mapper

import com.softbinator.data.network.entities.PhotoRemote
import com.softbinator.domain.model.AnimalPhoto

fun PhotoRemote.toPhoto(): AnimalPhoto = AnimalPhoto(
    small = small,
    medium = medium,
    large = large,
    full = full
)