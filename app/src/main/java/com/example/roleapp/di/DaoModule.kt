package com.example.roleapp.di

import android.content.Context
import androidx.room.Room
import com.example.roleapp.data.local.AppDatabase
import com.example.roleapp.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java,"app_database").build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase) : UserDao {
        return database.userDao()
    }
}