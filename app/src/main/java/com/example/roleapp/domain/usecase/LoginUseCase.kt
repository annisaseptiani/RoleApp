package com.example.roleapp.domain.usecase

import android.util.Log
import com.example.roleapp.domain.repository.user.UserRepository
import java.security.MessageDigest
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(email : String, password : String) : String {
        val encryptedPassword = encryptPassword(password)
        val userLogin = userRepository.login(email, encryptedPassword);
        Log.d("TAG", "execute: $encryptedPassword")
        var role = ""
        if (userLogin!=null) {
            userRepository.saveUser(email, encryptedPassword)
            role = userLogin.role
            return role
        }
        return role
    }

    fun encryptPassword(password: String) : String {
        val bytes = password.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}