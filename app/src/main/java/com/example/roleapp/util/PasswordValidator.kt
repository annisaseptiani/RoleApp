package com.example.roleapp.util

class PasswordValidator {
    fun isValidPassword(password:String) : Boolean {
        return password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } &&
                password.any { it.isDigit() }
    }
}