package com.softbinator.presentation.state

import com.softbinator.domain.model.Breed

data class AnimalItem(
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val age: String,
    val photos: List<PhotoItem> = arrayListOf(),
    val description: String?,
    val breeds: Breed?,
    val publishedAt: String,
) {
    companion object {
        val EMPTY = AnimalItem(
            id = -1,
            name = "",
            gender = "",
            status = "",
            species = "",
            age = "",
            photos = arrayListOf(),
            description = null,
            breeds = null,
            publishedAt = ""
        )
    }
}
