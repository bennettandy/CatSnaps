package com.avsoftware.catsnaps.data.remote

import com.avsoftware.catsnaps.data.model.BreedDto
import com.avsoftware.catsnaps.data.model.CatImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("breeds")
    suspend fun getBreeds(): List<BreedDto>

    @GET("images/search?has_breeds=true&size=small&mime_types=jpg&format=json&include_breed=1&order=ASC")
    suspend fun getImagesByBreed(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("breed_ids") breedId: String
    ): List<CatImageDto>
}
