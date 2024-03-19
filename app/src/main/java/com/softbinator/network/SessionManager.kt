package com.softbinator.network

import androidx.datastore.preferences.core.edit
import com.softbinator.presentation.AUTH_TOKEN_PREFERENCE
import com.softbinator.presentation.EXPIRES_IN_PREFERENCE
import com.softbinator.SoftbinatorApplication
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
class SessionManager @Inject constructor() {

    val accessToken: String? by lazy {
        val context = SoftbinatorApplication.instance.applicationContext
        runBlocking {
            context.dataStore.data
                .map { preferences ->
                    preferences[AUTH_TOKEN_PREFERENCE] ?: ""
                }.firstOrNull()
        }
    }

    val accessTokenExpired: Boolean by lazy {
        val context = SoftbinatorApplication.instance.applicationContext
        runBlocking {
            val accessTokenExpirationTime = context.dataStore.data
                .map { preferences ->
                    preferences[EXPIRES_IN_PREFERENCE]
                }.firstOrNull() ?: System.currentTimeMillis()
            val currentTimeMillis = System.currentTimeMillis()
            currentTimeMillis > accessTokenExpirationTime
        }
    }

    fun updateAccessToken(token: String, expiresIn: Int) {
        val context = SoftbinatorApplication.instance.applicationContext
        val expiry = System.currentTimeMillis() + expiresIn * 1000
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit {
                it[AUTH_TOKEN_PREFERENCE] = token
                it[EXPIRES_IN_PREFERENCE] = expiry
            }
        }
    }

}