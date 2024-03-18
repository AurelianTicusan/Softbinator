package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("small") var small: String = "",
    @SerializedName("medium") var medium: String = "",
    @SerializedName("large") var large: String = "",
    @SerializedName("full") var full: String = ""
)
