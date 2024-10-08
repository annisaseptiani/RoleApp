package com.example.roleapp.data.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.roleapp.data.model.Photos
import java.io.IOException

class UserPagingResource(private val api: RemoteApi) : PagingSource<Int, Photos>() {
    override fun getRefreshKey(state: PagingState<Int, Photos>): Int? {
        return state.anchorPosition?.let {
            anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.getListPhotos(page = currentPage, limit = 10)
    //        val nextPage = if (response.list.isEmpty()) null else currentPage + 1
            if (response.isNotEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = if (currentPage ==1) null else currentPage -1,
                    nextKey = if (response.isEmpty()) null else currentPage + 1

                )
            } else {
                LoadResult.Error(IOException("Error: data null"))
            }

        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}