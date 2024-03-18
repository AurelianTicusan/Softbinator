package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class AnimalsResponse(
    @SerializedName("animals") val animals: List<Animal> = arrayListOf(),
    @SerializedName("pagination") val pagination: Pagination? = Pagination()
)
