package com.example.roleapp.domain.usecase

import android.content.SharedPreferences
import java.security.MessageDigest

class VerifyPasswordUseCase(private val sharedPreferences: SharedPreferences)  {
    fun execute(inputPassword: String): Boolean {
        val storedPassword = sharedPreferences.getString("password", null)
        val encryptedInputPass = encryptPassword(inputPassword)
        return storedPassword == encryptedInputPass
    }

    private fun encryptPassword(password: String) : String{
        val bytes = password.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }

    }
}