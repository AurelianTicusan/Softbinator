package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class Breed(
    @SerializedName("primary") var primary: String = "",
    @SerializedName("secondary") var secondary: String = "",
    @SerializedName("mixed") var mixed: Boolean? = null,
    @SerializedName("unknown") var unknown: Boolean? = null
)
