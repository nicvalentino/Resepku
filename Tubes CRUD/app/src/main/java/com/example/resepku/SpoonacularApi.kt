package com.example.resepku

import com.example.resepku.model.FoodRecipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.resepku.model.Result

public interface SpoonacularApi {

    @GET("recipes/complexSearch")
    fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean?,
        @Query("number") number: Int?,
        @Query("offset") offset: Int?
    ): Call<FoodRecipe>

    @GET("recipes/{id}/information")
    fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Call<Result>
}