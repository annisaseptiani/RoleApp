package com.example.roleapp.userpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.roleapp.domain.usecase.LoadPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel constructor(private val photosUseCase: LoadPhotosUseCase)
    : ViewModel() {
    val pagingFlow = photosUseCase().cachedIn(viewModelScope)

}