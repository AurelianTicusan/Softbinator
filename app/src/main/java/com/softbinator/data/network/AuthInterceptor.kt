package com.softbinator.data.network

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val oAuthApi: OAuthApi,
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = sessionManager.accessToken

        if (accessToken.isNullOrEmpty() || sessionManager.accessTokenExpired) {
            val refreshedToken = runBlocking {
                val response = oAuthApi.refreshToken().blockingGet()
                sessionManager.updateAccessToken(response.accessToken, response.expiresIn)
                response.accessToken
            }

            if (refreshedToken.isNotEmpty()) {
                // Create a new request with the refreshed access token
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken")
                    .build()

                // Retry the request with the new access token
                return chain.proceed(newRequest)
            }
        }

        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(authorizedRequest)
    }
}