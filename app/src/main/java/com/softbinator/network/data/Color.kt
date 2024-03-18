package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("primary") var primary: String = "",
    @SerializedName("secondary") var secondary: String = "",
    @SerializedName("tertiary") var tertiary: String = ""
)
