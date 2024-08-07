package com.example.roleapp.domain.usecase

import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import com.example.roleapp.domain.repository.user.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class LoginUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        loginUseCase = LoginUseCase(userRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test successful login`() = runBlockingTest {
        val email = "test@example.com"
        val password = "password"
        val name = "nana"
        val role = "admin"
        val id = 1
        val encryptedPassword = loginUseCase.encryptPassword(password)

        // Mock the userRepository behavior
        `when`(userRepository.login(email, encryptedPassword)).thenReturn(UserEntity(id, name, email, encryptedPassword, role))

        // Execute the use case
        val result = loginUseCase.execute(email, password)

        // Verify the interactions and result
        verify(userRepository).login(email, encryptedPassword)
        verify(userRepository).saveUser(email, encryptedPassword)
        assertEquals("", result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test unsuccessful login`() = runBlockingTest {
        val email = "test@example.com"
        val password = "password"
        val encryptedPassword = loginUseCase.encryptPassword(password)

        // Mock the userRepository behavior
        `when`(userRepository.login(email, encryptedPassword)).thenReturn(null)

        // Execute the use case
        val result = loginUseCase.execute(email, password)

        // Verify the interactions and result
        verify(userRepository).login(email, encryptedPassword)
        verify(userRepository, never()).saveUser(email, encryptedPassword)
        assertEquals("", result)
    }
}