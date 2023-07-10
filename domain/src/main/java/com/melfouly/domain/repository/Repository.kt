package com.melfouly.domain.repository

import com.melfouly.domain.model.CategoryResponse

interface Repository {
    suspend fun getMealsFromRemote(): CategoryResponse
}