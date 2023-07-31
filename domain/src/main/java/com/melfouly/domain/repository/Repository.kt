package com.melfouly.domain.repository

import com.melfouly.domain.model.CategoryResponse
import com.melfouly.domain.model.MealsResponse

interface Repository {
    suspend fun getCategoriesFromRemote(): CategoryResponse

    suspend fun getMealsFromRemote(category: String): MealsResponse
}