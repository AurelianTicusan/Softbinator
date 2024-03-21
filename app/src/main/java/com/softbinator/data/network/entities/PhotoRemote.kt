package com.softbinator.data.network.entities

import com.google.gson.annotations.SerializedName

data class PhotoRemote(
    @SerializedName("small") var small: String? = "",
    @SerializedName("medium") var medium: String? = "",
    @SerializedName("large") var large: String? = "",
    @SerializedName("full") var full: String? = ""
)
