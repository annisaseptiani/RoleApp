package com.example.roleapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class UserPreferences (context: Context) {
        private val sharedPreferences: SharedPreferences =
            getEncryptedSharedPreferences(context)

        fun saveUser(email: String, password:String) {
            sharedPreferences.edit().putString("email", email).apply()
            sharedPreferences.edit().putString("password", password).apply()
        }

        fun getPassword(): String? {
            return sharedPreferences.getString("password", null)
        }

        fun clearUser() {
            sharedPreferences.edit().remove("email").apply()
            sharedPreferences.edit().remove("password").apply()
        }

    fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            "user_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}