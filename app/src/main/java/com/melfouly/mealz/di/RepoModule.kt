package com.melfouly.mealz.di

import com.melfouly.data.remote.ApiService
import com.melfouly.data.repository.RepositoryImpl
import com.melfouly.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }
}