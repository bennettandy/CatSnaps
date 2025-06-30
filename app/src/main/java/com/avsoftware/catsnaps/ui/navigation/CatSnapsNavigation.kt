package com.avsoftware.catsnaps.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage
import com.avsoftware.catsnaps.ui.breed.BreedSearchScreen
import com.avsoftware.catsnaps.ui.images.CatImages
import com.avsoftware.catsnaps.ui.mvi.CatSnapsIntent
import com.avsoftware.catsnaps.ui.mvi.CatSnapsUiState
import com.avsoftware.catsnaps.ui.splash.CatSplash
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CatSnapsNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: CatSnapsUiState,
    handleIntent: (CatSnapsIntent) -> Unit,
    getPaginatedImages: (CatBreed) -> Flow<PagingData<CatImage>>
) {
    // provides the Shared scope to enable shared element transitions
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = modifier
        ) {
            composable(
                route = "splash"
            ) {
                CatSplash(onNavigateToMain = {
                    navController.navigate("search") {
                        popUpTo("splash") { inclusive = true }
                    }
                })
            }

            composable(
                route = "search"
            ) {
                BreedSearchScreen(
                    uiState = uiState,
                    handleIntent = handleIntent,
                    onBreedSelected = { breed ->
                        handleIntent(CatSnapsIntent.SelectBreed(breed))
                        navController.navigate("images")
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }

            composable(
                route = "images"
            ) {
                uiState.selectedBreed?.let { breed ->
                    CatImages(
                        modifier = Modifier,
                        selectedBreed = breed,
                        getPaginatedImages = getPaginatedImages,
                        onImageClick = { handleIntent(CatSnapsIntent.CatImageClicked(it, breed)) },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }
        }
    }
}