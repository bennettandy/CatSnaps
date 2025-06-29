package com.avsoftware.catsnaps.ui.mvi

sealed class CatSnapsSideEffect {
    data class ShowSnackbar(val message: String) : CatSnapsSideEffect()
    // Add more side effects as needed (e.g., navigation, toast)
}