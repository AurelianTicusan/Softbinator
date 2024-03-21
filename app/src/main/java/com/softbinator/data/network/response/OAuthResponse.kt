package com.softbinator.data.network.response

import com.google.gson.annotations.SerializedName

data class OAuthResponse(
    @SerializedName("token_type") var tokenType: String = "Bearer",
    @SerializedName("expires_in") var expiresIn: Int = 3600,
    @SerializedName("access_token") var accessToken: String = ""
)
