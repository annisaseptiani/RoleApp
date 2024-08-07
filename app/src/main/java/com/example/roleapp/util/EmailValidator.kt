package com.example.roleapp.util

import android.text.TextUtils
import javax.inject.Inject

class EmailValidator @Inject constructor() {
    fun isValidEmail(email : String) : Boolean {
        return if (email.isEmpty()) {
            false
        } else {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            email.matches(emailRegex.toRegex())
        }
    }
}