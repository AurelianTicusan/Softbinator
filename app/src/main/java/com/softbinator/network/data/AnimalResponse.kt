package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class AnimalResponse(
    @SerializedName("animal") val animal: Animal = Animal(),
)
