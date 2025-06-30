package com.avsoftware.catsnaps.ui.common

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.withSharedTransitionElement(
    key: String,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    tweenDurationMillis: Int = 400
) = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
    // allows us to keep the transition and visibility scopes optional
    // while reducing duplicated boiler plate code
    with(sharedTransitionScope) {
        Modifier.sharedElement(
            sharedContentState = rememberSharedContentState(key = key),
            animatedVisibilityScope = animatedVisibilityScope,
            boundsTransform = { _, _ ->
                tween(durationMillis = tweenDurationMillis)
            }
        )
    }
} else {
    this
}