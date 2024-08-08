package com.example.roleapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Customize or return the response
        val cacheControl = response.header("Cache-Control")
        return if (cacheControl == null) {
            response.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60 * 60 * 24)
                .build()
        } else {
            response
        }
    }
}