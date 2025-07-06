package com.avsoftware.catsnaps.data

import com.avsoftware.data.CatClient
import com.avsoftware.data.model.BreedDto
import com.avsoftware.data.util.onError
import com.avsoftware.data.util.onSuccess
import com.avsoftware.domain.model.CatBreed
import com.avsoftware.domain.usecase.GetBreedsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetBreedsRetrofitUseCase @Inject constructor(
    private val catClient: CatClient
) : GetBreedsUseCase {
    private var breedCache: List<CatBreed>? = null
    private val mutex = Mutex() // this should prevent concurrent calls to the API
    private var isFetching = false

    override suspend fun getBreeds(searchString: String): Flow<List<CatBreed>> = flow {
        mutex.withLock {
            // Return cached breeds if available
            val cachedBreeds = breedCache
            if (cachedBreeds != null) {
                Timber.d("Returning Cached Breed List")
                emit(filterBreeds(cachedBreeds, searchString))
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
                catClient.getBreeds()
                    .onSuccess {
                        breedCache = it.map { breed: BreedDto -> breed.toDomain()}

                        emit(filterBreeds(breedCache?.toList() ?: emptyList(), searchString))

                    }
                    .onError {
                    // FIXME - handle error properly
                        Timber.e("Failed to get breed list ${it.name}")
                    }


            } catch (e: Exception) {
                throw IOException("Failed to fetch breeds: ${e.message}", e)
            } finally {
                isFetching = false
            }
        }
    }

    private fun filterBreeds(breeds: List<CatBreed>, filterString: String): List<CatBreed> {

        Timber.d("Filter ${breeds.size} Breeds [$filterString]")

        return if (filterString.isBlank()) {
            breeds
        } else {
            breeds.filter { it.name.contains(filterString, ignoreCase = true) }
        }
    }

    override fun clearCache() {
        breedCache = null
    }

    private fun BreedDto.toDomain() = CatBreed(
        id = id,
        name = name,
        description = description,
        temperament = temperament,
    )
}