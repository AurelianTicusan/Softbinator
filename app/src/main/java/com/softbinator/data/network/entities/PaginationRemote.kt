package com.softbinator.data.network.entities

import com.google.gson.annotations.SerializedName

data class PaginationRemote(
    @SerializedName("count_per_page") val countPerPage: Int? = -1,
    @SerializedName("total_count") val totalCount: Int? = -1,
    @SerializedName("current_page") val currentPage: Int? = -1,
    @SerializedName("total_pages") val totalPages: Int? = -1,
    @SerializedName("_links") val links: LinksRemote? = LinksRemote()
)
