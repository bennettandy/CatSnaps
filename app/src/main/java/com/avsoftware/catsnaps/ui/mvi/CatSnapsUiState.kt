package com.avsoftware.catsnaps.ui.mvi

import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage
import com.avsoftware.catsnaps.ui.common.LoadableList

data class CatSnapsUiState (
    val searchString: String,
    val selectedBreed: CatBreed?,
    val catBreeds: LoadableList<CatBreed>,
    val catImages: LoadableList<CatImage>
){
    companion object {
        val default = CatSnapsUiState(
            searchString = "",
            selectedBreed = null,
            catBreeds = LoadableList.Never,
            catImages = LoadableList.Never
        )
    }
}