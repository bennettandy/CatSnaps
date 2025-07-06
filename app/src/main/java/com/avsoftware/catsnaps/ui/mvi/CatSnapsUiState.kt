package com.avsoftware.catsnaps.ui.mvi

import com.avsoftware.catsnaps.ui.common.LoadableList
import com.avsoftware.domain.model.CatBreed

data class CatSnapsUiState (
    val searchString: String,
    val selectedBreed: CatBreed?,
    val catBreeds: LoadableList<CatBreed>,
){
    companion object {
        val default = CatSnapsUiState(
            searchString = "",
            selectedBreed = null,
            catBreeds = LoadableList.Never,
        )
    }
}