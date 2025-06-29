package com.avsoftware.catsnaps.ui.breed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.avsoftware.catsnaps.R
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.ui.common.LoadableList
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import com.avsoftware.catsnaps.ui.mvi.CatSnapsIntent
import com.avsoftware.catsnaps.ui.mvi.CatSnapsUiState
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme

@Composable
fun BreedSearchScreen(
    uiState: CatSnapsUiState,
    handleIntent: (CatSnapsIntent) -> Unit,
    onBreedSelected: (CatBreed) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Cat Breed Search Bar
        BreedSearchBar(
            searchString = uiState.searchString,
            onSearchStringChange = { query ->
                handleIntent(CatSnapsIntent.UpdateSearchString(query))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
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
                ErrorPanel(breeds.message)
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
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = breeds.data,
                            key = { breed -> breed.id }
                        ) { breed ->
                            BreedItem(
                                breed = breed,
                                onClick = { onBreedSelected(breed) },
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

@Composable
private fun ErrorPanel(errorMessage: String){
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
    ) {

        Text(
            text = "Cat-astrophe! Our furry friends are tangled in a digital yarn. Retry in a bit! \uD83D\uDC31",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
        )

        ErrorAnimation()
    }
}

@Composable
private fun ErrorAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_lottie))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

@MultiThemePreview
@Composable
fun BreedSearchPreview() {
    CatSnapsTheme {
        BreedSearchScreen(
            uiState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Success(
                    listOf(
                        CatBreed(id = "beng", name = "Bengal", description = "a friendly cat", temperament = "Active"),
                        CatBreed(id = "pers", name = "Persian", description = "a friendly cat", temperament = "Quiet")
                    )
                ),
                searchString = ""
            ),
            onBreedSelected = {},
            handleIntent = {}
        )
    }
}

@MultiThemePreview
@Composable
fun BreedSearchEmptyPreview() {
    CatSnapsTheme {
        BreedSearchScreen(
            uiState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Success(
                    emptyList()
                ),
                searchString = ""
            ),
            onBreedSelected = {},
            handleIntent = {}
        )
    }
}

@MultiThemePreview
@Composable
fun BreedSearchInitialPreview() {
    CatSnapsTheme {
        BreedSearchScreen(
            uiState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Never,
                searchString = "search string"
            ),
            onBreedSelected = {},
            handleIntent = {}
        )
    }
}

@MultiThemePreview
@Composable
fun BreedSearchErrorPreview() {
    CatSnapsTheme {
        BreedSearchScreen(
            uiState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Error("failed to load any breeds"),
                searchString = "search string"
            ),
            onBreedSelected = {},
            handleIntent = {}
        )
    }
}