package com.avsoftware.catsnaps

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avsoftware.catsnaps.ui.breed.BreedSearch
import com.avsoftware.catsnaps.ui.mvi.CatSnapsViewModel
import com.avsoftware.catsnaps.ui.splash.CatSplash
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CatSnapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()


            val uiState = viewModel.container.stateFlow.collectAsState()

            CatSnapsTheme {

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ){
                    composable(
                        route = "splash"
                    ) {
                        CatSplash(onNavigateToMain = { navController.navigate("search") })
                    }

                    composable(
                        route = "search"
                    ) {
                        BreedSearch(
                            uiState = uiState.value,
                            handleIntent = viewModel::handleIntent
                        )
                    }
                }



            }
        }

        Timber.d("CAT API: viewmodel instance $viewModel")
    }
}
