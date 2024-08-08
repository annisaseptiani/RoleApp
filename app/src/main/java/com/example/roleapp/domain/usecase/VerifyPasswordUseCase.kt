package com.example.roleapp.domain.usecase

import android.content.SharedPreferences
import com.example.roleapp.data.local.UserPreferences
import com.example.roleapp.domain.repository.user.UserRepository
import java.security.MessageDigest
import javax.inject.Inject

class VerifyPasswordUseCase @Inject constructor(private val userRepository: UserRepository)  {
    fun execute(inputPassword: String): Boolean {
        val storedPassword : String? = userRepository.getUserPass()
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