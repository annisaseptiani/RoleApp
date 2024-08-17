package com.example.roleapp.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserPreferencesTest {

    private lateinit var userPreferences: UserPreferences
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        userPreferences = UserPreferences(context)
    }

    @Test
    fun saveemailandpassword() {
        userPreferences.saveUser("test@example.com", "password123")

        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val valEmail = sharedPrefs.getString("email", "test@example.com")
        val valPass = sharedPrefs.getString("password", "password123")
        assertEquals("test@example.com", valEmail)
        assertEquals("password123", valPass)
    }

    @Test
    fun returnsavedpassword() {
        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putString("password", "password123").apply()

        val password = userPreferences.getPassword()

        assertEquals("password123", password)
    }

    @Test
    fun removeemailandpasswordb() {
        userPreferences.clearUser()

        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        assertNull(sharedPrefs.getString("email", null))
        assertNull(sharedPrefs.getString("password", null))
    }
}