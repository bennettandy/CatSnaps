package com.avsoftware.catsnaps.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avsoftware.catsnaps.data.remote.CatApiService
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.usecase.GetBreedsUseCase
import com.avsoftware.catsnaps.ui.common.LoadableList
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.catch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import timber.log.Timber


@HiltViewModel
class CatSnapsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
) : ViewModel(), ContainerHost<CatSnapsUiState, CatSnapsSideEffect> {

    // Orbit container exposes UI state flow and side effect flows
    override val container =
        viewModelScope.container<CatSnapsUiState, CatSnapsSideEffect>(CatSnapsUiState.default)

    init {
        handleUpdateSearchString("")
    }

    fun handleIntent(intent: CatSnapsIntent) {
        when (intent) {
            is CatSnapsIntent.UpdateSearchString -> handleUpdateSearchString(intent.newSearchString)
            is CatSnapsIntent.SelectBreed -> handleSelectBreed(intent.breed)
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
                postSideEffect(CatSnapsSideEffect.ShowError("Failed to load breeds"))
            }
            .collect { breeds ->
                reduce {
                    state.copy(catBreeds = LoadableList.Success(breeds))
                }
            }

    }

    private fun handleSelectBreed(breed: CatBreed) = intent {

        Timber.d("BREED ${breed.name} SELECTED")
        reduce {
            state.copy(
                selectedBreed = breed
            )
        }
    }

}