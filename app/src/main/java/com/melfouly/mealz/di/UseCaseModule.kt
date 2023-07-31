package com.melfouly.mealz.di

import com.melfouly.domain.repository.Repository
import com.melfouly.domain.usecase.MainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(repository: Repository): MainUseCase {
        return MainUseCase(repository)
    }
}