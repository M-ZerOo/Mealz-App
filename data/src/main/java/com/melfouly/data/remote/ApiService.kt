package com.melfouly.data.remote

import com.melfouly.domain.model.CategoryResponse
import retrofit2.http.GET

interface ApiService {

    @GET("categories.php")
    suspend fun getMeals(): CategoryResponse
}