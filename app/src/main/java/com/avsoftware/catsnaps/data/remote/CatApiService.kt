package com.avsoftware.catsnaps.data.remote

import com.avsoftware.catsnaps.data.model.BreedDto
import com.avsoftware.catsnaps.data.model.CatImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("breeds")
    suspend fun getBreeds(): List<BreedDto>

    @GET("images/search")
    suspend fun getImagesByBreed(
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<CatImageDto>
}