package com.softbinator.data.network.entities

import com.google.gson.annotations.SerializedName

data class LinksRemote(
    @SerializedName("self") var next: LinkRemote? = LinkRemote(),
    @SerializedName("type") var previous: LinkRemote? = LinkRemote(),
    @SerializedName("organization") var self: LinkRemote? = LinkRemote()
)
