package com.example.roleapp.userpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.paging.cachedIn
import com.example.roleapp.domain.repository.user.UserRepository
import com.example.roleapp.domain.usecase.LoadPhotosUseCase
import com.example.roleapp.ui.theme.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val photosUseCase: LoadPhotosUseCase,
    private val userRepository: UserRepository)
    : ViewModel() {
    val pagingFlow = photosUseCase().cachedIn(viewModelScope)

    fun logout(navHostController: NavHostController) {
        userRepository.loggedOut()
        navHostController.navigate(Routes.Login.routes) {
            popUpTo(Routes.AdminPage.routes) {
                inclusive = true
            }
        }
    }

}