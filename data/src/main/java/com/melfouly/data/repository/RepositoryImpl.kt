package com.melfouly.data.repository

import com.melfouly.data.remote.ApiService
import com.melfouly.domain.model.CategoryResponse
import com.melfouly.domain.repository.Repository

class RepositoryImpl(private val apiService: ApiService): Repository {
    override suspend fun getMealsFromRemote(): CategoryResponse = apiService.getMeals()
}