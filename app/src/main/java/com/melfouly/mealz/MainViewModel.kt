package com.melfouly.mealz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melfouly.domain.model.CategoryResponse
import com.melfouly.domain.usecase.GetMealz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getMealzUseCase: GetMealz) : ViewModel() {

    private val _categories = MutableStateFlow<CategoryResponse?>(null)
    val categories: StateFlow<CategoryResponse?> get() = _categories

    fun getMeals() {
        viewModelScope.launch {
            try {
                _categories.value = getMealzUseCase()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message.toString())
            }
        }
    }
}