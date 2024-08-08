package com.example.roleapp.domain.usecase

import android.util.Log
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.repository.user.UserRepository
import javax.inject.Inject

class UpdateDataUseCase @Inject constructor(
    private val userRepository: UserRepository)
{
    suspend fun execute(email : String, username: String, role: String, id: Int) : Boolean {
        return try {
            val entity: UserEntity? = userRepository.getUserByEmail(email)
            if (entity != null) {
                userRepository.update(email = email, name = username, role = role, id = id)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.d("UpdateUseCase", e.message.toString())
            false
        }
    }
}