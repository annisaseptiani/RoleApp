package com.example.roleapp.domain.repository.user

import android.content.Context
import android.content.SharedPreferences
import com.example.roleapp.data.local.UserDao
import com.example.roleapp.data.local.UserPreferences
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class IUserRepositoryTest {

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userPreferences: UserPreferences

    private lateinit var userRepository: IUserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRepository = IUserRepository(userDao, userPreferences)
    }

    @Test
    fun `login should return UserEntity when user exists`() = runTest {
        val userEntity = UserEntity(1, "John Doe", "john.doe@example.com", "password123", "user")
        `when`(userDao.loginUser(anyString(), anyString())).thenReturn(userEntity)

        val result = userRepository.login("john.doe@example.com", "password123")

        assertNotNull(result)
        assertEquals(userEntity.id, result?.id)
        assertEquals(userEntity.name, result?.name)
        assertEquals(userEntity.email, result?.email)
        assertEquals(userEntity.password, result?.password)
        assertEquals(userEntity.role, result?.role)
    }

    @Test
    fun `login should return null when user does not exist`() = runTest {
        `when`(userDao.loginUser(anyString(), anyString())).thenReturn(null)

        val result = userRepository.login("john.doe@example.com", "wrongpassword")

        assertNull(result)
    }

    @Test
    fun `register should call userDao registerUser`() = runTest {
        val user = User("John Doe", "john.doe@example.com", "password123", "user")

        userRepository.register(user)

        verify(userDao, times(1)).registerUser(any(UserEntity::class.java))
    }

    @Test
    fun `getAllUser should return flow of user list`() = runTest {
        val userEntities = listOf(UserEntity(1, "John Doe", "john.doe@example.com", "password123", "user"))
        `when`(userDao.getAllUser()).thenReturn(flowOf(userEntities))

        val result = userRepository.getAllUser().toList()

        assertEquals(1, result.size)
        assertEquals(userEntities, result[0])
    }

    @Test
    fun `update should call userDao editUser`() = runTest {
        userRepository.update("John Doe", "john.doe@example.com", "admin", 1)

        verify(userDao, times(1)).editUser(anyString(), anyString(), anyString(), anyInt())
    }

    @Test
    fun `delete should call userDao deleteUser`() = runTest {
        userRepository.delete(1)

        verify(userDao, times(1)).deleteUser(1)
    }

    @Test
    fun `saveUser should call sharedUserPreferences saveUser`() = runTest {
        userRepository.saveUser("john.doe@example.com", "password123")

        verify(userPreferences, times(1)).saveUser(anyString(), anyString())
    }

    @Test
    fun `getUserByEmail should return UserEntity when user exists`() = runTest {
        val userEntity = UserEntity(1, "John Doe", "john.doe@example.com", "password123", "user")
        `when`(userDao.getUserByEmail(anyString())).thenReturn(userEntity)

        val result = userRepository.getUserByEmail("john.doe@example.com")

        assertNotNull(result)
        assertEquals(userEntity.id, result?.id)
        assertEquals(userEntity.name, result?.name)
        assertEquals(userEntity.email, result?.email)
        assertEquals(userEntity.password, result?.password)
        assertEquals(userEntity.role, result?.role)
    }

    @Test
    fun `loggedOut should call sharedUserPreferences clearUser`() = runTest {
        userRepository.loggedOut()

        verify(userPreferences, times(1)).clearUser()
    }

    @Test
    fun `getUserPass should return password from sharedUserPreferences`() = runTest {
        `when`(userPreferences.getPassword()).thenReturn("password123")

        val result = userRepository.getUserPass()

        assertEquals("password123", result)
    }
}

// UserRepository interface for testing
public interface UserRepository {
    suspend fun login(username: String, password: String): UserEntity?
    fun getAllUser(): Flow<List<UserEntity>>
    suspend fun register(user: User)
    suspend fun update(name: String, email: String, role: String, id: Int)
    suspend fun delete(id: Int): Boolean
    suspend fun saveUser(email: String, password: String)
    suspend fun getUserByEmail(email: String): UserEntity?
    suspend fun getRole(role: String): String
    fun loggedOut()

    fun getUserPass() :String?
}