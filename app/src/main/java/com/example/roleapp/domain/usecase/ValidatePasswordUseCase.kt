package com.example.roleapp.domain.usecase

import com.example.roleapp.util.PasswordValidator
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(
    private val passwordValidator: PasswordValidator) {

    fun execute(password : String) : Boolean {
        return passwordValidator.isValidPassword(password)
    }
}