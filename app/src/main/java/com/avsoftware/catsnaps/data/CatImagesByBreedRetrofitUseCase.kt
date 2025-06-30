package com.avsoftware.catsnaps.data

import com.avsoftware.catsnaps.data.model.CatImageDto
import com.avsoftware.catsnaps.data.remote.CatApiService
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage
import com.avsoftware.catsnaps.domain.usecase.CatImagesByBreedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatImagesByBreedRetrofitUseCase(
    private val catApiService: CatApiService

): CatImagesByBreedUseCase {

    override fun getImages(
        breed: CatBreed,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<CatImage>> {
        return flow {
            val images = catApiService.getImagesByBreed(
                breedId = breed.id,
                limit = pageSize,
                page = pageNumber
            ).map { it.toDomain(pageNumber) }
            emit(images)
        }
    }

    private fun CatImageDto.toDomain(pageNumber: Int) = CatImage(
        id = id,
        url = url,
        width = width,
        height = height,
    )
}