package com.avsoftware.catsnaps.domain.usecase

import com.avsoftware.catsnaps.domain.model.CatBreed
import kotlinx.coroutines.flow.Flow

interface GetBreedsUseCase {
    suspend fun getBreeds(searchString: String): Flow<List<CatBreed>>
}