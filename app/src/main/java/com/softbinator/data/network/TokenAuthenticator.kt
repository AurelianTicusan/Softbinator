package com.softbinator.data.network

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val oAuthApi: OAuthApi,
    private val sessionManager: SessionManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val accessToken = sessionManager.accessToken

        if (accessToken.isNullOrEmpty() || sessionManager.accessTokenExpired) {
            val refreshedToken = runBlocking {
                val auth = oAuthApi.refreshToken().blockingGet()
                sessionManager.updateAccessToken(auth.accessToken, auth.expiresIn)
                auth.accessToken
            }

            if (refreshedToken.isNotEmpty()) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken")
                    .build()

            }
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

    }
}