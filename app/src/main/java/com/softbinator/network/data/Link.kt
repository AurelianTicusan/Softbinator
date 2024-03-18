package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("href") var href: String = ""
)
