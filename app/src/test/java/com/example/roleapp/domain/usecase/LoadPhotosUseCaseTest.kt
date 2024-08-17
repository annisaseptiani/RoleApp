package com.example.roleapp.domain.usecase

import androidx.paging.PagingData
import com.example.roleapp.data.model.Photos
import com.example.roleapp.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class LoadPhotosUseCaseTest {

    private lateinit var repository: RemoteRepository
    private lateinit var loadPhotosUseCase: LoadPhotosUseCase

    @Before
    fun setUp() {
        repository = mock()
        loadPhotosUseCase = LoadPhotosUseCase(repository)
    }

    @Test
    fun `invoke should return flow of PagingData from repository`() = runTest {

        val photos = PagingData.from(listOf(
            Photos(albumId = 1, id = 1, title = "Photo 1", url = "url1", thumbnailUrl = "thumbnal1"),
            Photos(albumId = 1, id = 2, title = "Photo 2", url = "url2", thumbnailUrl = "thumbnal2")
        ))
        `when`(repository.getAllData()).thenReturn(flowOf(photos))

        // Act
        val result: Flow<PagingData<Photos>> = loadPhotosUseCase()

        // Assert
        val resultList = result.toList()
        assertEquals(1, resultList.size) // Ensure that we have one PagingData item
        assertEquals(photos, resultList[0]) // Ensure that the PagingData is as expected

        verify(repository).getAllData()
    }
}