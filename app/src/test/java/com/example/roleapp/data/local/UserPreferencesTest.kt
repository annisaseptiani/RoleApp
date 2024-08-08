package com.example.roleapp.data.local

import android.content.Context
import android.content.SharedPreferences
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserPreferencesTest{
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var userPreferences: UserPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)

        userPreferences = UserPreferences(context)
    }

    @Test
    fun `saveUser should save email and password`() {
        userPreferences.saveUser("test@example.com", "password123")

        verify(editor).putString("email", "test@example.com")
        verify(editor).putString("password", "password123")
        verify(editor, times(2)).apply()
    }

    @Test
    fun `getPassword should return saved password`() {
        `when`(sharedPreferences.getString("password", null)).thenReturn("password123")

        val password = userPreferences.getPassword()

        assertEquals("password123", password)
    }

    @Test
    fun `clearUser should remove email and password`() {
        userPreferences.clearUser()

        verify(editor).remove("email")
        verify(editor).remove("password")
        verify(editor, times(2)).apply()
    }
}