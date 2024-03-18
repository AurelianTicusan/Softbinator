package com.softbinator

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val AUTH_TOKEN_PREFERENCE = stringPreferencesKey("authToken")
val EXPIRES_IN_PREFERENCE = longPreferencesKey("expiresIn")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "preferences.json"
)