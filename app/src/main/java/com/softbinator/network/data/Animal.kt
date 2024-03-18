package com.softbinator.network.data

import com.google.gson.annotations.SerializedName

data class Animal(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("organization_id") var organizationId: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("breeds") var breeds: Breed = Breed(),
    @SerializedName("size") var size: String = "",
    @SerializedName("gender") var gender: String = "",
    @SerializedName("status") var status: String = "",
    @SerializedName("distance") var distance: String = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("type") var type: String = "",
    @SerializedName("species") var species: String = "",
    @SerializedName("colors") var colors: Color = Color(),
    @SerializedName("age") var age: String = "",
    @SerializedName("coat") var coat: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("photos") var photos: ArrayList<Photo> = arrayListOf(),
    @SerializedName("status_changed_at") var statusChangedAt: String = "",
    @SerializedName("published_at") var publishedAt: String = "",
)
