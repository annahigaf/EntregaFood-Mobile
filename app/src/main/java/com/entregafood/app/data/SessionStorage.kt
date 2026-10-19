package com.entregafood.app.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/** Guarda a sessão criptografada (Android Keystore). */
class SessionStorage(private val context: Context) {

    private val prefs by lazy {
        val key = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "entregafood_session",
            key,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    fun load(): Session? = try {
        val token = prefs.getString("token", null)
        val id = prefs.getString("id", null)
        val name = prefs.getString("name", null)
        val email = prefs.getString("email", null)
        if (token != null && id != null && name != null && email != null) {
            Session(token, User(id, name, email))
        } else null
    } catch (e: Exception) {
        null // armazenamento corrompido: segue deslogado
    }

    fun save(s: Session) {
        prefs.edit()
            .putString("token", s.token)
            .putString("id", s.user.id)
            .putString("name", s.user.name)
            .putString("email", s.user.email)
            .apply()
    }

    fun clear() {
        try { prefs.edit().clear().apply() } catch (_: Exception) {}
    }
}
