package com.melfouly.data.remote

import com.melfouly.domain.model.CategoryResponse
import com.melfouly.domain.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getCategoryMeals(@Query("c") category: String): MealsResponse
}