package com.avsoftware.catsnaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.avsoftware.catsnaps.ui.mvi.CatSnapsIntent
import com.avsoftware.catsnaps.ui.mvi.CatSnapsSideEffect
import com.avsoftware.catsnaps.ui.mvi.CatSnapsViewModel
import com.avsoftware.catsnaps.ui.navigation.CatSnapsNavigation
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme
import com.avsoftware.domain.model.CatBreed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CatSnapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val uiState = viewModel.container.stateFlow.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.container.sideEffectFlow.collect {
                    sideEffect: CatSnapsSideEffect ->
                    when (sideEffect){
                        is CatSnapsSideEffect.ShowSnackbar -> launch {
                            // dismissing any existing snackbar here to prevent multiple instances stacking up
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = sideEffect.message,
                                duration = SnackbarDuration.Short,
                            )
                        }
                    }
                }
            }

            LaunchedEffect(Unit) {
                viewModel.handleIntent(CatSnapsIntent.ReloadBreeds)
            }

            CatSnapsTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { insets ->
                    CatSnapsNavigation(
                        modifier = Modifier.padding(insets),
                        uiState = uiState.value,
                        navController = navController,
                        handleIntent = viewModel::handleIntent,
                        getPaginatedImages = { breed: CatBreed -> viewModel.getImages(breed) }
                    )
                }
            }
        }
    }
}
