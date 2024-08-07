package com.example.roleapp.domain.usecase

import androidx.paging.PagingData
import com.example.roleapp.data.model.Photos
import com.example.roleapp.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadPhotosUseCase @Inject constructor(private val repository: RemoteRepository) {
    operator fun invoke() : Flow<PagingData<Photos>> {
        return repository.getAllData()
    }
}