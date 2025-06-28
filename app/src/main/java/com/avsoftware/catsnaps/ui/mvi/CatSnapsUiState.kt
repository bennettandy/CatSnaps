package com.avsoftware.catsnaps.ui.mvi

import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage

data class CatSnapsUiState (
    val searchString: String,
    val catBreeds: List<CatBreed>,
    val catImages: List<CatImage>
){
    companion object {
        val default = CatSnapsUiState(
            searchString = "fixme",
            catBreeds = emptyList(),
            catImages = emptyList()
        )
    }
}