package com.avsoftware.catsnaps.ui.common

sealed interface LoadableList<out T> {
    data object Never : LoadableList<Nothing>
    data object Loading : LoadableList<Nothing>
    data class Error(val message: String) : LoadableList<Nothing>
    data class Success<out T>(val data: List<T>) : LoadableList<T>
}