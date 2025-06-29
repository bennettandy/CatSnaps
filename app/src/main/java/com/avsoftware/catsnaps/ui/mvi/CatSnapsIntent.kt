package com.avsoftware.catsnaps.ui.mvi

import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage

sealed interface CatSnapsIntent {
    data object ReloadBreeds: CatSnapsIntent
    data class UpdateSearchString(val newSearchString: String): CatSnapsIntent
    data class SelectBreed(val breed: CatBreed) : CatSnapsIntent
    data class CatImageClicked(val catImage: CatImage, val catBreed: CatBreed) : CatSnapsIntent
}