package com.example.roleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roleapp.data.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity)

    @Query("SELECT * FROM user_table")
    fun getAllUser() : kotlinx.coroutines.flow.Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    suspend fun loginUser(email : String, password : String) : UserEntity?

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUser(id : Int)

    @Query("UPDATE user_table SET name = :name, email = :email, role = :role WHERE id = :id")
    suspend fun editUser(name : String, email : String, role: String,
                         id: Int)

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserByEmail(email: String) : UserEntity?
}