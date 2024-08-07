package com.example.roleapp.domain.usecase

import com.example.roleapp.util.EmailValidator
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val validator: EmailValidator
) {
   fun execute(email : String) : Boolean {
       return validator.isValidEmail(email)
   }
}