package com.avsoftware.catsnaps.ui.breed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.ui.common.LoadableList
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import com.avsoftware.catsnaps.ui.mvi.CatSnapsIntent
import com.avsoftware.catsnaps.ui.mvi.CatSnapsUiState
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme

@Composable
fun BreedSearch(
    uiState: CatSnapsUiState,
    handleIntent: (CatSnapsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
//            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Cat Breed Search Bar
        BreedSearchBar(
            searchString = uiState.searchString,
            onSearchStringChange = { query ->
                handleIntent(CatSnapsIntent.UpdateSearchString(query))
            },
//            onTriggerSearch = {
//                handleIntent(CatSnapsIntent.LoadBreeds)
//            },
            modifier = Modifier.fillMaxWidth()
        )

        // Cat Breed List
        when (val breeds = uiState.catBreeds) {
            is LoadableList.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
            is LoadableList.Error -> {
                Text(
                    text = breeds.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
            is LoadableList.Success -> {

                if (breeds.data.isEmpty()) {
                    Text(
                        text = if (uiState.searchString.isBlank()) "No breeds available" else "No breeds match your search",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                } else {
                    // Used some AI here to help untangle this crucial composable
                    // correct items function not being found.. turned out just to be
                    // Android Studio getting imports confused
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = breeds.data,
                            key = { breed -> breed.id }
                        ) { breed ->
                            BreedItem(
                                breed = breed,
                                onClick = { handleIntent(CatSnapsIntent.SelectBreed(breed)) },
                            )
                        }
                    }
                }
            }

            is LoadableList.Never -> {
                Text(
                    text = "Search for cat breeds",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}


@MultiThemePreview
@Composable
fun BreedSearchPreview() {
    CatSnapsTheme {
        BreedSearch(
            uiState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Success(
                    listOf(
                        CatBreed(id = "beng", name = "Bengal", temperament = "Active"),
                        CatBreed(id = "pers", name = "Persian", temperament = "Quiet")
                    )
                ),
                searchString = ""
            ),
            handleIntent = {}
        )
    }
}

@MultiThemePreview
@Composable
fun BreedSearchEmptyPreview() {
    CatSnapsTheme {
        BreedSearch(
            uiState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Success(
                    emptyList()
                ),
                searchString = ""
            ),
            handleIntent = {}
        )
    }
}

@MultiThemePreview
@Composable
fun BreedSearchInitialPreview() {
    CatSnapsTheme {
        BreedSearch(
            uiState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Never,
                searchString = "search string"
            ),
            handleIntent = {}
        )
    }
}