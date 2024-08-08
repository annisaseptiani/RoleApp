package com.example.roleapp.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roleapp.domain.model.User
import com.example.roleapp.domain.usecase.RegisterUseCase
import com.example.roleapp.domain.usecase.ValidateEmailUseCase
import com.example.roleapp.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val passwordUseCase: ValidatePasswordUseCase) : ViewModel(){

    private var _registerResult = MutableLiveData<Boolean?>()
    val registerResult : LiveData<Boolean?> get() = _registerResult
    private var _isEmailValid = MutableLiveData<Boolean?>()
    val emailValidator : LiveData<Boolean?> get() = _isEmailValid
    private var isPassStrong = MutableLiveData<Boolean?>()
    val passwordValidator : LiveData<Boolean?> get() = isPassStrong

    fun register(name : String, email: String,
                 password: String, role : String) {
        val user : User = User(name, email, password, role)
        viewModelScope.launch {
            _registerResult.value = registerUseCase.execute(user)
        }
    }

    fun validateEmail(email: String) {
        _isEmailValid.value = validateEmailUseCase.execute(email)
    }

    fun validatePassword(password: String) {
        isPassStrong.value = passwordUseCase.execute(password)
    }

    fun resetState() {
        _registerResult.value = null
        _isEmailValid.value = null
        isPassStrong.value = null
    }
}