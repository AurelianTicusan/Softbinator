package com.softbinator.data.network

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.softbinator.presentation.AUTH_TOKEN_PREFERENCE
import com.softbinator.presentation.EXPIRES_IN_PREFERENCE
import com.softbinator.presentation.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val context: Context
) {

    private var _accessToken: String? = null
    private var _expiryDate: Long = -1L

    val accessToken: String? by lazy {
        val accessToken = _accessToken
        if (accessToken.isNullOrEmpty()) {
            runBlocking {
                context.dataStore.data
                    .map { preferences ->
                        preferences[AUTH_TOKEN_PREFERENCE] ?: ""
                    }.firstOrNull()
            }
        } else {
            accessToken
        }
    }

    val accessTokenExpired: Boolean by lazy {
        val expiryDate = _expiryDate
        if (expiryDate == -1L) {
            runBlocking {
                val accessTokenExpirationTime = context.dataStore.data
                    .map { preferences ->
                        preferences[EXPIRES_IN_PREFERENCE]
                    }.firstOrNull() ?: System.currentTimeMillis()
                val currentTimeMillis = System.currentTimeMillis()
                currentTimeMillis > accessTokenExpirationTime
            }
        } else {
            val currentTimeMillis = System.currentTimeMillis()
            currentTimeMillis > expiryDate
        }
    }

    fun updateAccessToken(token: String, expiresIn: Int) {
        val expiry = System.currentTimeMillis() + expiresIn * 1000
        _accessToken = token
        _expiryDate = expiry
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit {
                it[AUTH_TOKEN_PREFERENCE] = token
                it[EXPIRES_IN_PREFERENCE] = expiry
            }
        }
    }

}