package com.softbinator.data.network.entities

import com.google.gson.annotations.SerializedName

data class ColorRemote(
    @SerializedName("primary") var primary: String? = "",
    @SerializedName("secondary") var secondary: String? = "",
    @SerializedName("tertiary") var tertiary: String? = ""
)
