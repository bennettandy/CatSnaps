package com.avsoftware.catsnaps.ui.breed

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.avsoftware.catsnaps.R
import com.avsoftware.domain.model.CatBreed
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import com.avsoftware.catsnaps.ui.common.withSharedTransitionElement
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BreedItem(
    breed: CatBreed,
    showPhotosClicked: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
    Card(
        modifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "box-${breed.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .fillMaxWidth()
            }
        } else {
            modifier.fillMaxWidth()
        },

        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        border = CardDefaults.outlinedCardBorder(
            enabled = true
        )

    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = breed.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,


                    modifier = Modifier.withSharedTransitionElement(
                        key = "text-${breed.id}",
                        sharedTransitionScope,
                        animatedVisibilityScope
                    )
                )
            },

            supportingContent = {
                Text(
                    text = breed.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .withSharedTransitionElement(
                            key = "supporting-${breed.id}",
                            sharedTransitionScope,
                            animatedVisibilityScope
                        )
                        .fillMaxWidth()
                )
            },
            trailingContent = {
                    IconButton(
                        onClick = showPhotosClicked,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_camera_roll_24),
                            contentDescription = stringResource(R.string.cat_picture_button_content_desc, breed.name),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
            }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@MultiThemePreview
@Composable
fun BreedItemPreview() {
    CatSnapsTheme {
        BreedItem(
            breed = CatBreed(
                name = "Siamese",
                id = "sms",
                description = "Elegant and vocal, known for their striking blue eyes and sleek bodies.",
                temperament = "aloof"
            ),
            showPhotosClicked = {},
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        )
    }
}