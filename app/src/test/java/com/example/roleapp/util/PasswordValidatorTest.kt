package com.example.roleapp.util

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class PasswordValidatorTest {
    private lateinit var passwordValidator: PasswordValidator

    @Before
    fun setUp() {
        passwordValidator = PasswordValidator()
    }

    @Test
    fun `valid passwords`() {
        val validPasswords = listOf(
            "Password123",
            "Valid1234",
            "Uppercase1",
            "Password1",
            "TestPass1"
        )

        validPasswords.forEach { password ->
            assertTrue( passwordValidator.isValidPassword(password))
        }
    }

    @Test
    fun `invalid passwords too short`() {
        val invalidPasswords = listOf(
            "Pass1",
            "Short1",
            "Tiny1",
            "12345A"
        )

        invalidPasswords.forEach { password ->
            assertFalse( passwordValidator.isValidPassword(password))
        }
    }

    @Test
    fun `invalid passwords missing digits`() {
        val invalidPasswords = listOf(
            "Password",
            "NoNumbers",
            "AllLetters",
            "Uppercase"
        )

        invalidPasswords.forEach { password ->
            assertFalse( passwordValidator.isValidPassword(password))
        }
    }

    @Test
    fun `invalid passwords missing uppercase letters`() {
        val invalidPasswords = listOf(
            "password123",
            "alllowercase1",
            "nouppercase2"
        )

        invalidPasswords.forEach { password ->
            assertFalse( passwordValidator.isValidPassword(password))
        }
    }

    @Test
    fun `invalid passwords missing uppercase and digits`() {
        val invalidPasswords = listOf(
            "password",
            "lowercase",
            "onlyletters"
        )

        invalidPasswords.forEach { password ->
            assertFalse( passwordValidator.isValidPassword(password))
        }
    }
}