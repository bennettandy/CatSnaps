package com.avsoftware.domain.model.usecase

import com.avsoftware.domain.model.CatBreed
import kotlinx.coroutines.flow.Flow

interface GetBreedsUseCase {
    suspend fun getBreeds(searchString: String): Flow<List<CatBreed>>
    fun clearCache()
}