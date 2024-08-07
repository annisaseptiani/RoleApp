package com.example.roleapp.util

import android.text.TextUtils

class EmailValidator {
    fun isValidEmail(email : String) : Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }
}