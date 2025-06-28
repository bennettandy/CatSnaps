package com.avsoftware.catsnaps.domain.usecase

import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage
import kotlinx.coroutines.flow.Flow

interface CatImagesByBreedUseCase {
    fun getImages(breed: CatBreed, pageNumber: Int, pageSize: Int): Flow<List<CatImage>>
}