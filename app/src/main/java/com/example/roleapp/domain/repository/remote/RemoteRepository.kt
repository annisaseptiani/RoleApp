package com.example.roleapp.domain.repository.remote

import androidx.paging.PagingData
import com.example.roleapp.data.model.Photos
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
   fun getAllData() : Flow<PagingData<Photos>>
}