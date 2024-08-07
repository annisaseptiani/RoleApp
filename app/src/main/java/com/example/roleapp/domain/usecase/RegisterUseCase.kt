package com.example.roleapp.domain.usecase

import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import com.example.roleapp.domain.repository.user.UserRepository
import java.security.MessageDigest
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository) {
    suspend fun execute(user: User) : Boolean {
        val existingUser = userRepository.getUserByEmail(user.email)
        return if (existingUser==null) {
            userRepository.register(User(username = user.username,
                email = user.email, password = encryptPassword(user.password),
                user.role))
            true
        } else {
            false
        }
    }

    fun encryptPassword(password: String) : String {
        val bytes = password.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}