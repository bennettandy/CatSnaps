package com.avsoftware.catsnaps.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.avsoftware.catsnaps.data.paging.CatImagePagingSource
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage
import com.avsoftware.catsnaps.domain.usecase.CatImagesByBreedUseCase
import com.avsoftware.catsnaps.domain.usecase.GetBreedsUseCase
import com.avsoftware.catsnaps.ui.common.LoadableList
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import timber.log.Timber


@HiltViewModel
class CatSnapsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val catImagesByBreedUseCase: CatImagesByBreedUseCase
) : ViewModel(), ContainerHost<CatSnapsUiState, CatSnapsSideEffect> {

    // Orbit container exposes UI state flow and side effect flows
    override val container =
        viewModelScope.container<CatSnapsUiState, CatSnapsSideEffect>(CatSnapsUiState.default)

    init {
        handleUpdateSearchString("")
    }

    // paginated cat images if everything works correctly
    fun getImages(breed: CatBreed): Flow<PagingData<CatImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                CatImagePagingSource(
                    catImagesByBreedUseCase,
                    breed,
                    pageSize = 10
                )
            }
        ).flow
    }

    fun handleIntent(intent: CatSnapsIntent) {
        Timber.d("Handle Intent: $intent")
        when (intent) {
            is CatSnapsIntent.ReloadBreeds -> handleReloadBreeds()
            is CatSnapsIntent.UpdateSearchString -> handleUpdateSearchString(intent.newSearchString)
            is CatSnapsIntent.SelectBreed -> handleSelectBreed(intent.breed)
            is CatSnapsIntent.CatImageClicked -> handleCatImageClicked(
                catImage = intent.catImage,
                catBreed = intent.catBreed
            )
        }
    }

    private fun handleUpdateSearchString(newString: String) = intent {
        reduce {
            state.copy(
                searchString = newString
            )
        }

        getBreedsUseCase.getBreeds(state.searchString)
            .catch { e ->
                reduce {
                    state.copy(
                        catBreeds = LoadableList.Error(
                            e.message ?: "Failed to load breeds"
                        )
                    )
                }
                postSideEffect(CatSnapsSideEffect.ShowSnackbar("Failed to load breeds"))
            }
            .collect { breeds ->
                reduce {
                    state.copy(catBreeds = LoadableList.Success(breeds))
                }
            }
    }

    private fun handleReloadBreeds() = intent {
        reduce {
            // reset breeds to initial unloaded state
            state.copy(catBreeds = LoadableList.Never)
        }
        getBreedsUseCase.clearCache()
        delay(300) // gives ui time to clear if we have an immediate failure
        handleIntent(CatSnapsIntent.UpdateSearchString(state.searchString))
    }

    private fun handleSelectBreed(breed: CatBreed) = intent {
        reduce {
            state.copy(
                selectedBreed = breed
            )
        }
    }

    private fun handleCatImageClicked(catImage: CatImage, catBreed: CatBreed) = intent {
        postSideEffect(
            CatSnapsSideEffect.ShowSnackbar(
                "A picture of a ${catBreed.name} with id [${catImage.id}] "
            )
        )
    }
}