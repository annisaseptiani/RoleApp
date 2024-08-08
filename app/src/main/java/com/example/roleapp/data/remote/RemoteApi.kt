package com.example.roleapp.data.remote

import com.example.roleapp.data.model.Photos
import com.example.roleapp.data.model.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {

    @GET("/photos")
    suspend fun getListPhotos(@Query("page") page : Int,
                              @Query("limit") limit : Int) : List<Photos>
}