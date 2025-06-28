package com.avsoftware.catsnaps.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avsoftware.catsnaps.data.remote.CatApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import timber.log.Timber


@HiltViewModel
class CatSnapsViewModel @Inject constructor(
    private val apiService: CatApiService
) : ViewModel(), ContainerHost<CatSnapsUiState, CatSnapsSideEffect> {

    // Orbit container exposes UI state flow and side effect flows
    override val container =
        viewModelScope.container<CatSnapsUiState, CatSnapsSideEffect>(CatSnapsUiState.default)

    init {
        Timber.d("INITIALISED VIEW MODEL - api $apiService")
    }

}