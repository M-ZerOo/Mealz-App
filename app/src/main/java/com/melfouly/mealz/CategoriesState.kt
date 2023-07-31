package com.melfouly.mealz

import com.melfouly.domain.model.CategoryResponse

data class CategoriesState(
    val success: CategoryResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
