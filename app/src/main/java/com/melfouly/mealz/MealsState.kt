package com.melfouly.mealz

import com.melfouly.domain.model.MealsResponse

data class MealsState(
    val success: MealsResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
