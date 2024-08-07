package com.example.roleapp.di

import android.content.Context
import com.example.roleapp.data.local.UserDao
import com.example.roleapp.data.remote.RemoteApi
import com.example.roleapp.domain.repository.remote.IRemoteRepository
import com.example.roleapp.domain.repository.remote.RemoteRepository
import com.example.roleapp.domain.repository.user.IUserRepository
import com.example.roleapp.domain.repository.user.UserRepository
import com.example.roleapp.domain.usecase.LoadPhotosUseCase
import com.example.roleapp.domain.usecase.LoginUseCase
import com.example.roleapp.domain.usecase.RegisterUseCase
import com.example.roleapp.domain.usecase.UpdateDataUseCase
import com.example.roleapp.domain.usecase.ValidateEmailUseCase
import com.example.roleapp.domain.usecase.ValidatePasswordUseCase
import com.example.roleapp.ui.theme.navigation.Routes
import com.example.roleapp.util.EmailValidator
import com.example.roleapp.util.PasswordValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteRepository(api: RemoteApi) : RemoteRepository {
        return IRemoteRepository(api)
    }

    @Provides
    @Singleton
    fun providePhotosUseCase(repository: RemoteRepository) : LoadPhotosUseCase {
        return LoadPhotosUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao, context: Context) : UserRepository {
        return IUserRepository(dao, context)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(userRepository: UserRepository) : LoginUseCase {
        return LoginUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(userRepository: UserRepository) : RegisterUseCase {
        return RegisterUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateDataUseCase(userRepository: UserRepository) : UpdateDataUseCase {
        return UpdateDataUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun providePasswordValidator(passwordValidator: PasswordValidator) : ValidatePasswordUseCase {
        return ValidatePasswordUseCase(passwordValidator)
    }
    @Provides
    @Singleton
    fun provideEmailValidatorUseCase(emailValidator: EmailValidator) : ValidateEmailUseCase {
        return ValidateEmailUseCase(emailValidator)
    }

}