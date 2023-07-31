package com.melfouly.data.repository

import com.melfouly.data.remote.ApiService
import com.melfouly.domain.model.CategoryResponse
import com.melfouly.domain.model.MealsResponse
import com.melfouly.domain.repository.Repository

class RepositoryImpl(private val apiService: ApiService): Repository {
    override suspend fun getCategoriesFromRemote(): CategoryResponse = apiService.getCategories()
    override suspend fun getMealsFromRemote(category: String) = apiService.getCategoryMeals(category)
}