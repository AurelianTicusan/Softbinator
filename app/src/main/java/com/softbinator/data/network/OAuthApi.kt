package com.softbinator.data.network

import com.softbinator.CLIENT_ID
import com.softbinator.CLIENT_SECRET
import com.softbinator.GRANT_TYPE
import com.softbinator.data.network.response.OAuthResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OAuthApi {
    @FormUrlEncoded
    @POST("oauth2/token")
    fun refreshToken(
        @Field("grant_type") clientCredentials: String = GRANT_TYPE,
        @Field("client_id") clientId: String = CLIENT_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET
    ): Single<OAuthResponse>
}