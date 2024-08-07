package com.example.roleapp.data.local

import com.example.roleapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserDao {
    suspend fun loginUser(username: String, password: String): UserEntity?
    fun getAllUser(): Flow<List<UserEntity>>
    suspend fun registerUser(user: UserEntity)
    suspend fun editUser(name: String, email: String, role: String, id: Int)
    suspend fun deleteUser(id: Int): Boolean
    suspend fun getUserByEmail(email: String): UserEntity?
}
