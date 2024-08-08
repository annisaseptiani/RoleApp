package com.example.roleapp.domain.repository.user

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.roleapp.data.local.UserDao
import com.example.roleapp.data.local.UserPreferences
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IUserRepository @Inject constructor(private val userDao: UserDao,
    private val sharedUserPreferences: UserPreferences) : UserRepository {

    override suspend fun login(email: String, password: String) : UserEntity? {
        val userEntity = userDao.loginUser(email, password)

        return userEntity?.let { UserEntity(it.id, it.name, it.email, it.password, it.role) }
    }

    override fun getAllUser(): Flow<List<UserEntity>> {
        return userDao.getAllUser()
    }

    override suspend fun register(user: User) {
        val userEntity = UserEntity(name = user.username,
            email = user.email, password = user.password,
            role = user.role)
        userDao.registerUser(userEntity)
    }

    override suspend fun update(name: String, email: String,
                                role: String, id: Int) {
        userDao.editUser(name, email, role, id)
    }

    override suspend fun delete(id: Int) {
        userDao.deleteUser(id)
    }

    override suspend fun saveUser(email: String, password: String) {
        sharedUserPreferences.saveUser(email, password)
    }

    override suspend fun getUserByEmail(email: String) : UserEntity? {
        val userEntity = userDao?.getUserByEmail(email)
        return userEntity?.let { UserEntity(it.id, it.name, it.email, it.password, it.role) }
    }

    override suspend fun getRole(role: String): String {
        return role
    }

    override fun loggedOut() {
        sharedUserPreferences.clearUser()
    }

    override fun getUserPass() : String? {
        return sharedUserPreferences.getPassword()
    }


}