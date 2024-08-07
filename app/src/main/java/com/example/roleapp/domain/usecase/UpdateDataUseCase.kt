package com.example.roleapp.domain.usecase

import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.repository.user.UserRepository
import javax.inject.Inject

class UpdateDataUseCase @Inject constructor(
    private val userRepository: UserRepository)
{
    suspend fun execute(email : String, username: String, role: String, id: Int) : Boolean {
        val entity: UserEntity? = userRepository.getUserByEmail(email)
        userRepository.update(email, username, role, id)
        return entity!!.email == email && entity.name == username && entity.role == role
    }
}