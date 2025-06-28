package com.avsoftware.catsnaps.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.avsoftware.catsnaps.ui.breed.BreedSearch
import com.avsoftware.catsnaps.ui.images.CatImages
import com.avsoftware.catsnaps.ui.mvi.CatSnapsIntent
import com.avsoftware.catsnaps.ui.mvi.CatSnapsUiState
import com.avsoftware.catsnaps.ui.splash.CatSplash

@Composable
fun CatSnapsNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: CatSnapsUiState,
    handleIntent: (CatSnapsIntent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable(
            route = "splash"
        ) {
            CatSplash(onNavigateToMain = { navController.navigate("search") })
        }

        composable(
            route = "search"
        ) {
            BreedSearch(
                uiState = uiState,
                handleIntent = handleIntent
            )
        }

        composable(
            route = "images"
        ) {
            CatImages(

            )
        }
    }
}