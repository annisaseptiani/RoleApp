package com.example.roleapp.adminpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.domain.model.User
import com.example.roleapp.domain.repository.user.UserRepository
import com.example.roleapp.domain.usecase.UpdateDataUseCase
import com.example.roleapp.domain.usecase.ValidateEmailUseCase
import com.example.roleapp.domain.usecase.ValidatePasswordUseCase
import com.example.roleapp.domain.usecase.VerifyPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val repository: UserRepository,
    private val verifyPasswordUseCase: VerifyPasswordUseCase,
    private val updateDataUseCase: UpdateDataUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase) : ViewModel() {

    private val _deleteItem = MutableStateFlow<Boolean?>(false)
    val deleteItem : StateFlow<Boolean?> get() = _deleteItem

    private val _userList = MutableStateFlow<List<UserEntity>>(emptyList())
    val userList : StateFlow<List<UserEntity>> get() = _userList

    val _isDeleteDialogOpen = MutableStateFlow(false)
    val isDeleteDialogOpen = _isDeleteDialogOpen.asStateFlow()

    val _isEditDialogOpen = MutableStateFlow(false)
    val isEditDialogOpen = _isEditDialogOpen.asStateFlow()

    private val _verifyPass = MutableStateFlow<Boolean?>(false)
    val verifyPass : StateFlow<Boolean?> get() = _verifyPass

    private val _editItem = MutableStateFlow<Boolean?>(false)
    val editItem : StateFlow<Boolean?> get() = _editItem

    private var _isEmailValid = MutableLiveData<Boolean>()
    val emailValidator : LiveData<Boolean> get() = _isEmailValid

    fun showDeleteDialog() {
        _isDeleteDialogOpen.value = true
    }

    fun hideDeleteDialog() {
        _isDeleteDialogOpen.value = false
    }

    fun showEditDialog() {
        _isEditDialogOpen.value = true
    }

    fun hideEditDialog() {
        _isEditDialogOpen.value = false
    }

    init {
        listUser()
    }

    fun listUser() {
        viewModelScope.launch {
            _userList.value = repository.getAllUser().firstOrNull() ?: emptyList()
        }
    }

    fun deleteItem(id : Int) {
        try {
            viewModelScope.launch {
                repository.delete(id)
                _deleteItem.value = true
            }
        } catch (e: Exception) {
            _deleteItem.value = false
        }

    }

    fun verifyPassword(password: String) {
        viewModelScope.launch {
            _verifyPass.value = verifyPasswordUseCase.execute(password)
        }
    }

    fun editItem(name : String, role: String, email: String, id: Int) {
        viewModelScope.launch {
            _editItem.value = updateDataUseCase.execute(email, name, role, id)
        }
    }

    fun validateEmail(email: String) {
        _isEmailValid.value = validateEmailUseCase.execute(email)
    }

}