package com.example.roleapp.util

import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EmailValidatorTest {
    private lateinit var emailValidator: EmailValidator

    @Before
    fun setUp() {
        emailValidator = EmailValidator()
    }

    @Test
    fun `valid email addresses`() {
        val validEmails = listOf(
            "test@example.com",
            "user.name+tag+sorting@example.com",
            "user.name@example.com",
            "user_name@example.com",
            "user-name@example.com",
            "user@example.co.in",
            "user@sub.example.com"
        )

        validEmails.forEach { email ->
            assertTrue(emailValidator.isValidEmail(email))
        }
    }

    @Test
    fun `invalid email addresses`() {
        val invalidEmails = listOf(
            "plainaddress",
            "@missingusername.com",
            "username@.com",
            "username@.com.com",
            ".username@example.com",
            "username@.com.",
            "username@.com..com",
            "username@example..com",
            "username@-example.com",
            "username@.example.com",
            "username@ex..com",
            "username@.ex.com"
        )

        invalidEmails.forEach { email ->
            assertFalse(emailValidator.isValidEmail(email))
        }
    }
}