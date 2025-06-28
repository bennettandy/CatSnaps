package com.avsoftware.catsnaps.data

import com.avsoftware.catsnaps.data.model.BreedDto
import com.avsoftware.catsnaps.data.remote.CatApiService
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.usecase.GetBreedsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetBreedsRetrofitUseCase @Inject constructor(
    private val catApiService: CatApiService
) : GetBreedsUseCase {
    private var breedCache: List<CatBreed>? = null
    private val mutex = Mutex() // this should prevent concurrent calls to the API
    private var isFetching = false

    override suspend fun getBreeds(filterString: String): Flow<List<CatBreed>> = flow {
        mutex.withLock {
            // Return cached breeds if available
            val cachedBreeds = breedCache
            if (cachedBreeds != null) {
                Timber.d("Returning Cached Breed List")
                emit(filterBreeds(cachedBreeds, filterString))
                return@flow
            }

            // Skip if an API call is already in flight
            if (isFetching) {
                Timber.d("API request is already in flight so skip")
                return@flow
            }

            // Fetch from API
            Timber.d("New API Request")
            try {
                isFetching = true
                val breeds = catApiService.getBreeds().map { it.toDomain() }
                breedCache = breeds
                emit(filterBreeds(breeds, filterString))
            } catch (e: Exception) {
                throw IOException("Failed to fetch breeds: ${e.message}", e)
            } finally {
                isFetching = false
            }
        }
    }

    private fun filterBreeds(breeds: List<CatBreed>, filterString: String): List<CatBreed> {
        return if (filterString.isBlank()) {
            breeds
        } else {
            breeds.filter { it.name.contains(filterString, ignoreCase = true) }
        }
    }

    fun clearCache() {
        breedCache = null
    }

    private fun BreedDto.toDomain() = CatBreed(
        id = id,
        name = name,
        temperament = temperament,
    )
}