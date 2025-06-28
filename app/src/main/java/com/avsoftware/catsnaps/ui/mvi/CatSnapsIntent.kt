package com.avsoftware.catsnaps.ui.mvi

sealed interface CatSnapsIntent {
    data class UpdateSearchString(val newSearchString: String): CatSnapsIntent
}