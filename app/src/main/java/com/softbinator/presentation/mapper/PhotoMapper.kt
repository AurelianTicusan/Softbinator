package com.softbinator.presentation.mapper

import com.softbinator.domain.model.AnimalPhoto
import com.softbinator.presentation.state.PhotoItem

fun AnimalPhoto.toPhotoItem(): PhotoItem = PhotoItem(
    small = small,
    medium = medium,
    large = large,
    full = full
)