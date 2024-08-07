package com.example.roleapp.domain.repository.user

import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(username : String, password:String) : UserEntity?
    fun getAllUser() : Flow<List<UserEntity>>
    suspend fun register(user: User)
    suspend fun update(name : String, email : String,
                       role: String, id: Int)
    suspend fun delete(id: Int)
    suspend fun saveUser(email: String, password: String)
    suspend fun getUserByEmail(email: String) : UserEntity?
    suspend fun getRole(role:String) : String
}