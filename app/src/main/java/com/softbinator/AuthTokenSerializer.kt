package com.softbinator

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class AuthTokenSerializer(
    private val cryptoManager: CryptoManager
) : Serializer<AuthToken> {

    override val defaultValue: AuthToken
        get() = AuthToken()

    override suspend fun readFrom(input: InputStream): AuthToken {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = AuthToken.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AuthToken, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = AuthToken.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}