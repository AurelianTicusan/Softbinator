package com.softbinator.data.network.entities

import com.google.gson.annotations.SerializedName

data class LinkRemote(
    @SerializedName("href") var href: String? = ""
)
