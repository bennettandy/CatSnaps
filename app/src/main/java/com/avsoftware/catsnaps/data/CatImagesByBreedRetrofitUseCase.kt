package com.avsoftware.catsnaps.data


import com.avsoftware.data.CatClient
import com.avsoftware.data.model.CatImageDto
import com.avsoftware.data.util.onError
import com.avsoftware.data.util.onSuccess
import com.avsoftware.domain.model.CatBreed
import com.avsoftware.domain.model.CatImage
import com.avsoftware.domain.usecase.CatImagesByBreedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatImagesByBreedRetrofitUseCase(
    private val catClient: CatClient

): CatImagesByBreedUseCase {

    override fun getImages(
        breed: CatBreed,
        pageNumber: Int,
        pageSize: Int
    ): Flow<List<CatImage>> {
        return flow {
            catClient.getImagesByBreed(
                breedId = breed.id,
                limit = pageSize,
                page = pageNumber
            ).onSuccess {
                emit(it.map { it.toDomain() })
            }.onError {
                emit(emptyList()) // fixme - emit proper error
            }
        }
    }

    private fun CatImageDto.toDomain() = CatImage(
        id = id,
        url = url,
        width = width,
        height = height,
    )
}