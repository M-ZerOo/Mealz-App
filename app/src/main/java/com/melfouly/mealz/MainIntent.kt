package com.melfouly.mealz

sealed class MainIntent {
    object FetchCategories: MainIntent()
    data class FetchMeals(val categoryName: String) : MainIntent()
}
