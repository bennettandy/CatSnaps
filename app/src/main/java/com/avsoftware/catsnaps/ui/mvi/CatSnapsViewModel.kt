package com.avsoftware.catsnaps.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avsoftware.catsnaps.data.remote.CatApiService
import com.avsoftware.catsnaps.domain.model.CatBreed
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import timber.log.Timber


@HiltViewModel
class CatSnapsViewModel @Inject constructor(
    private val apiService: CatApiService // implement use case
) : ViewModel(), ContainerHost<CatSnapsUiState, CatSnapsSideEffect> {

    // Orbit container exposes UI state flow and side effect flows
    override val container =
        viewModelScope.container<CatSnapsUiState, CatSnapsSideEffect>(CatSnapsUiState.default)

    init {
        Timber.d("INITIALISED VIEW MODEL - api $apiService")
    }

    fun handleIntent(intent: CatSnapsIntent){
        when (intent){
            is CatSnapsIntent.UpdateSearchString -> handleUpdateSearchString(intent.newSearchString)
            is CatSnapsIntent.LoadBreeds -> handleLoadBreeds()
            is CatSnapsIntent.SelectBreed -> handleSelectBreed(intent.breed)
        }
    }

    private fun handleUpdateSearchString(newString: String) = intent {
        reduce {
            state.copy(
                searchString = newString
            )
        }
    }

    private fun handleSelectBreed(breed: CatBreed) = intent {
        reduce {
            state.copy(
                selectedBreed = breed
            )
        }
    }

    private fun handleLoadBreeds() = intent {
        Timber.w("NOT IMPLEMENTED")
        postSideEffect(CatSnapsSideEffect.ShowError("Load breeds not implemented"))
    }
}