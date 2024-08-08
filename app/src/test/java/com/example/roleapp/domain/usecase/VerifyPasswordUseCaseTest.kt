package com.example.roleapp.domain.usecase

import com.example.roleapp.domain.repository.user.UserRepository
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.security.MessageDigest

@RunWith(MockitoJUnitRunner::class)
class VerifyPasswordUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var verifyPasswordUseCase: VerifyPasswordUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        verifyPasswordUseCase = VerifyPasswordUseCase(userRepository)
    }

    @Test
    fun `execute should return true when passwords match`() {
        val inputPassword = "password123"
        val encryptedPassword = encryptPassword(inputPassword)
        `when`(userRepository.getUserPass()).thenReturn(encryptedPassword)

        val result = verifyPasswordUseCase.execute(inputPassword)

        assertTrue(result)
        verify(userRepository).getUserPass()
    }

    @Test
    fun `execute should return false when passwords do not match`() {
        val inputPassword = "password123"
        val wrongPassword = "wrongPassword123"
        `when`(userRepository.getUserPass()).thenReturn(encryptPassword(wrongPassword))

        val result = verifyPasswordUseCase.execute(inputPassword)

        assertFalse(result)
        verify(userRepository).getUserPass()
    }

    private fun encryptPassword(password: String): String {
        val bytes = password.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}