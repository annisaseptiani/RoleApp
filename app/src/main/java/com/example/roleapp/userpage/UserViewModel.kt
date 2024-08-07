package com.example.roleapp.userpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.roleapp.domain.usecase.LoadPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val photosUseCase: LoadPhotosUseCase)
    : ViewModel() {
    val pagingFlow = photosUseCase().cachedIn(viewModelScope)

}