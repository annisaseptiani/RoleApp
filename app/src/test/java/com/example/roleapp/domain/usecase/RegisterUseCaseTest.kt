package com.example.roleapp.domain.usecase

import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import com.example.roleapp.domain.repository.user.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.any
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class RegisterUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        registerUseCase = RegisterUseCase(userRepository)
    }

    @Test
    fun `test successful registration`() = runTest {
        val user = User(username = "testUser", email = "test@example.com", password = "password", role = "user")
        val encryptedPassword = registerUseCase.encryptPassword(user.password)
        val newUser = user.copy(password = encryptedPassword)

        `when`(userRepository.getUserByEmail(user.email)).thenReturn(null)

        val result = registerUseCase.execute(user)

        verify(userRepository).getUserByEmail(user.email)
        verify(userRepository).register(newUser)
        assertEquals(true, result)
    }

    @Test
    fun `test registration with existing user`() = runBlockingTest {
        val user = User(username = "testUser", email = "test@example.com", password = "password", role = "user")
        val existingUser : UserEntity? = UserEntity(id =1, name = "existingUser", email = "test@example.com", password = "existingPassword", role = "user")

        `when`(userRepository.getUserByEmail(user.email)).thenReturn(existingUser)

        val result = registerUseCase.execute(user)

        verify(userRepository).getUserByEmail(user.email)
        verify(userRepository, never()).register(user)
        assertEquals(false, result)
    }
}
