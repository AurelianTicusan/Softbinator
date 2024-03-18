package com.softbinator

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val token: String? = null,
    val expiresIn: Long = System.currentTimeMillis()
)
