package com.example.roleapp.domain.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.roleapp.data.model.Photos
import com.example.roleapp.data.remote.RemoteApi
import com.example.roleapp.data.remote.UserPagingResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IRemoteRepository @Inject constructor(private val api: RemoteApi) : RemoteRepository {
    override fun getAllData(): Flow<PagingData<Photos>> {
        return Pager(PagingConfig(pageSize = 10)) {
            UserPagingResource(api)
        }.flow
    }
}