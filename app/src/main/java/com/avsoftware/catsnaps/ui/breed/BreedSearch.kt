package com.avsoftware.catsnaps.ui.breed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import com.avsoftware.catsnaps.ui.mvi.CatSnapsIntent
import com.avsoftware.catsnaps.ui.mvi.CatSnapsUiState

@Composable
fun BreedSearch(uiState: CatSnapsUiState, handleIntent: (CatSnapsIntent) -> Unit){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = "Cat Snaps - ${uiState.searchString}",
                modifier = Modifier.padding(innerPadding)
            )
            TextField(
                value = uiState.searchString,
                onValueChange = {
                    handleIntent(
                        CatSnapsIntent.UpdateSearchString(
                            it
                        )
                    )
                }
            )
        }
    }
}

@MultiThemePreview
@Composable
fun BreedSearchPreview() {
    BreedSearch (
        uiState = CatSnapsUiState.default,
        handleIntent = {}
    )
}