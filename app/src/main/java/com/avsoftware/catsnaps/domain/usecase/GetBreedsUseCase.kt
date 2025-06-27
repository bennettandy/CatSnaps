package com.avsoftware.catsnaps.domain.usecase

import com.avsoftware.catsnaps.domain.model.CatBreed

interface GetBreedsUseCase {
    suspend fun getBreeds(searchString: String): List<CatBreed>
}