package com.example.roleapp.domain.repository.user

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.roleapp.data.local.UserDao
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IUserRepository @Inject constructor(private val userDao: UserDao, private val context: Context) : UserRepository {
    private val sharedPreferences = getEncryptedSharedPreferences(context)

    override suspend fun login(username: String, password: String) : UserEntity? {
        val userEntity = userDao.loginUser(username,password)

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
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    override suspend fun getUserByEmail(email: String) : UserEntity? {
        val userEntity = userDao.getUserByEmail(email)
        return userEntity?.let { UserEntity(it.id, it.name, it.email, it.password, it.role) }!!
    }

    override suspend fun getRole(role: String): String {
        return role
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