package com.example.roleapp.domain.usecase

import com.example.roleapp.util.EmailValidator
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class ValidateEmailUseCaseTest {

    private lateinit var emailValidator: EmailValidator
    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        emailValidator = mock(EmailValidator::class.java)
        validateEmailUseCase = ValidateEmailUseCase(emailValidator)
    }

    @Test
    fun `test execute with valid email`() {
        val validEmail = "test@example.com"

        // Mock the behavior of emailValidator
        `when`(emailValidator.isValidEmail(validEmail)).thenReturn(true)

        // Execute the use case
        val result = validateEmailUseCase.execute(validEmail)

        // Verify the result
        assertTrue(result)
        verify(emailValidator).isValidEmail(validEmail)
    }

    @Test
    fun `test execute with invalid email`() {
        val invalidEmail = "invalid-email"

        // Mock the behavior of emailValidator
        `when`(emailValidator.isValidEmail(invalidEmail)).thenReturn(false)

        // Execute the use case
        val result = validateEmailUseCase.execute(invalidEmail)

        // Verify the result
        assertFalse(result)
        verify(emailValidator).isValidEmail(invalidEmail)
    }

}