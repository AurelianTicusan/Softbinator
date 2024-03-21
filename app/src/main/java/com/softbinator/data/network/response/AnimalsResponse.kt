package com.softbinator.data.network.response

import com.google.gson.annotations.SerializedName
import com.softbinator.data.network.entities.AnimalRemote
import com.softbinator.data.network.entities.PaginationRemote

data class AnimalsResponse(
    @SerializedName("animals") val animals: List<AnimalRemote> = arrayListOf(),
    @SerializedName("pagination") val pagination: PaginationRemote? = PaginationRemote()
)
