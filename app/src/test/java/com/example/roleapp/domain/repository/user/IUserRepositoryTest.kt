package com.example.roleapp.domain.repository.user

import android.content.Context
import android.content.SharedPreferences
import com.example.roleapp.data.local.UserDao
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class IUserRepositoryTest {

    private lateinit var userDao: UserDao
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userRepository: IUserRepository

    @Before
    fun setUp() {
        userDao = mock(UserDao::class.java)
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        userRepository = IUserRepository(userDao, context)

        // Mocking the getEncryptedSharedPreferences method
        val editor = mock(SharedPreferences.Editor::class.java)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putString(anyString(), anyString())).thenReturn(editor)
        whenever(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        whenever(sharedPreferences.getString(anyString(), anyOrNull())).thenReturn("mockedValue")

        // Replacing the actual encrypted shared preferences with the mock
        doReturn(sharedPreferences).`when`(userRepository).getEncryptedSharedPreferences(context)
    }

    @Test
    fun `test login success`() = runBlockingTest {
        val userEntity : UserEntity? = UserEntity(1, "testUser", "test@example.com", "password", "user")
        whenever(userDao.loginUser("testUser", "password")).thenReturn(userEntity)

        val result = userRepository.login("testUser", "password")

        verify(userDao).loginUser("testUser", "password")
        assertEquals(userEntity, result)
    }

    @Test
    fun `test getAllUser`() = runBlockingTest {
        val userList = listOf(UserEntity(1, "testUser", "test@example.com", "password", "user"))
        whenever(userDao.getAllUser()).thenReturn(flowOf(userList))

        val result = userRepository.getAllUser().first()

        verify(userDao).getAllUser()
        assertEquals(userList, result)
    }

    @Test
    fun `test register user`() = runBlockingTest {
        val user = User("testUser", "test@example.com", "password", "user")
        val userEntity = UserEntity(name = user.username, email = user.email, password = user.password, role = user.role)

        userRepository.register(user)

        verify(userDao).registerUser(userEntity)
    }

    @Test
    fun `test update user`() = runBlockingTest {
        userRepository.update("newName", "newEmail", "newRole", 1)

        verify(userDao).editUser("newName", "newEmail", "newRole", 1)
    }

    @Test
    fun `test delete user`() = runBlockingTest {
        whenever(userDao.deleteUser(1)).thenReturn(true)

        val result = userRepository.delete(1)

        verify(userDao).deleteUser(1)
        assertEquals(true, result)
    }

    @Test
    fun `test saveUser`() = runBlockingTest {
        userRepository.saveUser("test@example.com", "password")

        val editor = sharedPreferences.edit()
        verify(editor).putString("email", "test@example.com")
        verify(editor).putString("password", "password")
        verify(editor).apply()
    }

    @Test
    fun `test getUserByEmail`() = runBlockingTest {
        val userEntity = UserEntity(1, "testUser", "test@example.com", "password", "user")
        whenever(userDao.getUserByEmail("test@example.com")).thenReturn(userEntity)

        val result = userRepository.getUserByEmail("test@example.com")

        verify(userDao).getUserByEmail("test@example.com")
        assertEquals(userEntity, result)
    }

    @Test
    fun `test getRole`() = runBlockingTest {
        val role = userRepository.getRole("admin")

        assertEquals("admin", role)
    }
}

// UserRepository interface for testing
interface UserRepository {
    suspend fun login(username: String, password: String): UserEntity?
    fun getAllUser(): Flow<List<UserEntity>>
    suspend fun register(user: User)
    suspend fun update(name: String, email: String, role: String, id: Int)
    suspend fun delete(id: Int): Boolean
    suspend fun saveUser(email: String, password: String)
    suspend fun getUserByEmail(email: String): UserEntity?
    suspend fun getRole(role: String): String
}