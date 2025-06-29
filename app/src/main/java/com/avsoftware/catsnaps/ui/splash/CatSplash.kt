package com.avsoftware.catsnaps.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.avsoftware.catsnaps.R
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme
import kotlinx.coroutines.delay

@Composable
fun CatSplash(
    onNavigateToMain: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(5000)
        isVisible = false
        delay(500)
        onNavigateToMain()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn( animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(bottom = 32.dp),
                text = stringResource(R.string.splash_title),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            CatAnimation()

        }
    }
}

@MultiThemePreview
@Composable
fun SplashScreenPreview() {
    CatSnapsTheme {
        CatSplash(onNavigateToMain = {})
    }
}

@Composable
fun CatAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cat_tail_lottie))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}