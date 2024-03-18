package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self") var next: Link = Link(),
    @SerializedName("type") var previous: Link = Link(),
    @SerializedName("organization") var self: Link = Link()
)
