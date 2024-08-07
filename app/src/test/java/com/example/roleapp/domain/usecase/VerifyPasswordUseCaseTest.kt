package com.example.roleapp.domain.usecase

import android.content.SharedPreferences
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.security.MessageDigest

@RunWith(MockitoJUnitRunner::class)
class VerifyPasswordUseCaseTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var verifyPasswordUseCase: VerifyPasswordUseCase

    @Before
    fun setUp() {
        sharedPreferences = mock(SharedPreferences::class.java)
        verifyPasswordUseCase = VerifyPasswordUseCase(sharedPreferences)
    }

    @Test
    fun `test execute with correct password`() {
        val inputPassword = "password123"
        val encryptedPassword = encryptPassword(inputPassword)

        // Mock the SharedPreferences behavior
        whenever(sharedPreferences.getString("password", null)).thenReturn(encryptedPassword)

        // Execute the use case
        val result = verifyPasswordUseCase.execute(inputPassword)

        // Verify the result
        assertTrue(result)
    }

    @Test
    fun `test execute with incorrect password`() {
        val inputPassword = "password123"
        val storedPassword = "differentEncryptedPassword"

        // Mock the SharedPreferences behavior
        whenever(sharedPreferences.getString("password", null)).thenReturn(storedPassword)

        // Execute the use case
        val result = verifyPasswordUseCase.execute(inputPassword)

        // Verify the result
        assertFalse(result)
    }

    private fun encryptPassword(password: String): String {
        val bytes = password.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}