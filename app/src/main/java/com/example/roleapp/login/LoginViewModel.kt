package com.example.roleapp.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roleapp.domain.repository.user.UserRepository
import com.example.roleapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _loginResult = MutableLiveData<String?>()
    val loginResult: LiveData<String?> get() = _loginResult

    fun login(email: String, password: String) {
        try {
            viewModelScope.launch {
                val isLogin = loginUseCase.execute(email, password)
                _loginResult.value = isLogin
            }
        } catch (e: Exception) {
            Log.d("LoginViewModel", e.toString())
        }
    }

    fun resetState() {
        _loginResult.value = null
    }
}