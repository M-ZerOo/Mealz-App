package com.melfouly.domain.usecase

import com.melfouly.domain.repository.Repository

class GetMealz(private val repository: Repository) {
    suspend operator fun invoke() = repository.getMealsFromRemote()
}