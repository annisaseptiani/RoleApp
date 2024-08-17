package com.example.roleapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class NetworkInterceptorTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var client: OkHttpClient
    private lateinit var networkInterceptor: NetworkInterceptor

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        networkInterceptor = NetworkInterceptor()

        client = OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `interceptor adds Cache-Control header when not present`() {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("Test Response")
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()

        // Act
        val response = client.newCall(request).execute()

        // Assert
        val cacheControlHeader = response.header("Cache-Control")
        assertEquals("public, max-age=86400", cacheControlHeader)
    }

    @Test
    fun `interceptor does not modify Cache-Control header when already present`() {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Cache-Control", "public, max-age=3600")
            .setBody("Test Response")
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()

        // Act
        val response = client.newCall(request).execute()

        // Assert
        val cacheControlHeader = response.header("Cache-Control")
        assertEquals("public, max-age=3600", cacheControlHeader)
    }
}