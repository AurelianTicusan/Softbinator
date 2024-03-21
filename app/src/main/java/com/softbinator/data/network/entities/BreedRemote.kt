package com.softbinator.data.network.entities

import com.google.gson.annotations.SerializedName

data class BreedRemote(
    @SerializedName("primary") var primary: String? = "",
    @SerializedName("secondary") var secondary: String? = "",
    @SerializedName("mixed") var mixed: Boolean? = null,
    @SerializedName("unknown") var unknown: Boolean? = null
)
