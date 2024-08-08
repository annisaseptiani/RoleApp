package com.example.roleapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

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
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "user_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}