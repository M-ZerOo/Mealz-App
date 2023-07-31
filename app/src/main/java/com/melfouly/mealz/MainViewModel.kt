package com.melfouly.mealz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melfouly.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: MainUseCase) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _categories = MutableStateFlow<CategoriesState>(CategoriesState(isLoading = true))
    val categories: StateFlow<CategoriesState> get() = _categories

    private val _meals = MutableStateFlow<MealsState>(MealsState(isLoading = true))
    val meals: StateFlow<MealsState> get() = _meals


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchCategories -> getCategories()
                    is MainIntent.FetchMeals -> getMeals(it.categoryName)
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                _categories.value = _categories.value.copy(
                    success = useCase.getCategories(),
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("MainViewModel", "Categories: ${e.message.toString()}")
                _categories.value = _categories.value.copy(
                    success = null,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun getMeals(category: String) {
        viewModelScope.launch {
            try {
                _meals.value = _meals.value.copy(
                    success = useCase.getMeals(category),
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("MainViewModel", "Meals:${e.message.toString()}")
                _meals.value = _meals.value.copy(
                    success = null,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

}