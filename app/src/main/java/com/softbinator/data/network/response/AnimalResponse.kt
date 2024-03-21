package com.softbinator.data.network.response

import com.google.gson.annotations.SerializedName
import com.softbinator.data.network.entities.AnimalRemote

data class AnimalResponse(
    @SerializedName("animal") val animal: AnimalRemote = AnimalRemote(),
)
