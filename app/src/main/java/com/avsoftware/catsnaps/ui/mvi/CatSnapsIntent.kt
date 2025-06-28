package com.avsoftware.catsnaps.ui.mvi

import com.avsoftware.catsnaps.domain.model.CatBreed

sealed interface CatSnapsIntent {
    data class UpdateSearchString(val newSearchString: String): CatSnapsIntent
    data class SelectBreed(val breed: CatBreed) : CatSnapsIntent
    object LoadBreeds : CatSnapsIntent
}