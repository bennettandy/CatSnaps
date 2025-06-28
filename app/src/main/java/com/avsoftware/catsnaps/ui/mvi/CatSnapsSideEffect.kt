package com.avsoftware.catsnaps.ui.mvi

sealed class CatSnapsSideEffect {
    data class ShowError(val message: String) : CatSnapsSideEffect()
    // Add more side effects as needed (e.g., navigation, toast)
}