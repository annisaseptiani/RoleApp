package com.example.roleapp.domain.usecase

import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.repository.user.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


class UpdateDataUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var updateDataUseCase: UpdateDataUseCase

    @Before
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        updateDataUseCase = UpdateDataUseCase(userRepository)
    }

    @Test
    fun test_succesful_update_data() = runTest {
        val newEmail = "nana1@mail.com"
        val newName = "nana1"
        val newRole = "admin"
        val id = 1
        val userEntity = UserEntity(id, newName, newEmail, "123", newRole)
        `when`(userRepository.getUserByEmail(newEmail)).thenReturn(userEntity)
        val result = updateDataUseCase.execute(newEmail, newName, newRole, id)
        assertTrue(result)
        verify(userRepository).update(email = newEmail, name = newName, role = newRole, id = id)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `execute should return false when exception occurs`() = runTest {
        // Arrange
        val email = "test@example.com"
        val username = "newUsername"
        val role = "admin"
        val id = 1

        `when`(userRepository.getUserByEmail(email)).thenThrow(RuntimeException("Database error"))

        // Act
        val result = updateDataUseCase.execute(email, username, role, id)

        // Assert
        assertFalse(result)
        verify(userRepository, never()).update(anyString(), anyString(), anyString(), anyInt())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `execute should return false when user is not found`() = runTest {
        // Arrange
        val email = "test@example.com"
        val username = "newUsername"
        val role = "admin"
        val id = 1

        `when`(userRepository.getUserByEmail(email)).thenReturn(null)

        // Act
        val result = updateDataUseCase.execute(email, username, role, id)

        // Assert
        assertFalse(result)
        verify(userRepository, never()).update(anyString(), anyString(), anyString(), anyInt())
    }

}