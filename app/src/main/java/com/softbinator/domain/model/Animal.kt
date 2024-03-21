package com.softbinator.domain.model

data class Animal(
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val age: String,
    val photos: List<AnimalPhoto>,
    val description: String,
    val breeds: Breed?,
    val publishedAt: String,
)
