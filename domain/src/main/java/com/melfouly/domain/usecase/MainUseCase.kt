package com.melfouly.domain.usecase

import com.melfouly.domain.repository.Repository

class MainUseCase(private val repository: Repository) {
    suspend fun getCategories() = repository.getCategoriesFromRemote()

    suspend fun getMeals(category: String) = repository.getMealsFromRemote(category)
}