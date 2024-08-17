package com.example.roleapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.roleapp.data.model.Photos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.IOException

class UserPagingResourceTest {
    private lateinit var api: RemoteApi
    private lateinit var userPagingResource: UserPagingResource

    @Before
    fun setUp() {
        api = mock(RemoteApi::class.java)
        userPagingResource = UserPagingResource(api)
    }

    @Test
    fun `getRefreshKey should return the correct key`() {
        // Arrange
        val state = mock(PagingState::class.java) as PagingState<Int, Photos>
        val closestPage = PagingSource.LoadResult.Page(
            data = listOf<Photos>(),
            prevKey = 1,
            nextKey = 3
        )

        `when`(state.anchorPosition).thenReturn(5)
        `when`(state.closestPageToPosition(5)).thenReturn(closestPage)

        // Act
        val refreshKey = userPagingResource.getRefreshKey(state)

        // Assert
        assertEquals(2, refreshKey)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load should return page when data is loaded successfully`() = runTest {
        // Arrange
        val photos = listOf(
            Photos(albumId = 1, id = 1, title = "Photo 1", url = "url1", thumbnailUrl = "thumbnal1"),
            Photos(albumId = 1, id = 2, title = "Photo 2", url = "url2", thumbnailUrl = "thumbnal2")
        )
        `when`(api.getListPhotos(page = 1, limit = 10)).thenReturn(photos)

        // Act
        val result = userPagingResource.load(PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false))

        // Assert
        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(photos, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load should return error when data is empty`() = runTest {
        // Arrange
        `when`(api.getListPhotos(page = 1, limit = 10)).thenReturn(emptyList())

        // Act
        val result = userPagingResource.load(PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false))

        // Assert
        assertTrue(result is PagingSource.LoadResult.Error)
        val error = result as PagingSource.LoadResult.Error
        assertTrue(error.throwable is IOException)
        assertEquals("Error: data null", error.throwable.message)
    }
}