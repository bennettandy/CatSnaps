package com.avsoftware.domain.model.usecase

import com.avsoftware.domain.model.CatBreed
import com.avsoftware.domain.model.CatImage
import kotlinx.coroutines.flow.Flow

interface CatImagesByBreedUseCase {
    fun getImages(breed: CatBreed, pageNumber: Int, pageSize: Int): Flow<List<CatImage>>
}