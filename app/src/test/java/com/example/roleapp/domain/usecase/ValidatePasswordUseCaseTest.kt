package com.example.roleapp.domain.usecase

import com.example.roleapp.util.PasswordValidator
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


class ValidatePasswordUseCaseTest {
    private lateinit var passwordValidator: PasswordValidator
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

    @Before
    fun setUp() {
        passwordValidator = mock(PasswordValidator::class.java)
        validatePasswordUseCase = ValidatePasswordUseCase(passwordValidator)
    }

    @Test
    fun `test execute with valid password`() {
        val validPassword = "Password123"

        // Mock the behavior of passwordValidator
        `when`(passwordValidator.isValidPassword(validPassword)).thenReturn(true)

        // Execute the use case
        val result = validatePasswordUseCase.execute(validPassword)

        // Verify the result
        assertTrue(result)
        verify(passwordValidator).isValidPassword(validPassword)
    }

    @Test
    fun `test execute with invalid password`() {
        val invalidPassword = "pass"

        // Mock the behavior of passwordValidator
        `when`(passwordValidator.isValidPassword(invalidPassword)).thenReturn(false)

        // Execute the use case
        val result = validatePasswordUseCase.execute(invalidPassword)

        // Verify the result
        assertFalse(result)
        verify(passwordValidator).isValidPassword(invalidPassword)
    }
}