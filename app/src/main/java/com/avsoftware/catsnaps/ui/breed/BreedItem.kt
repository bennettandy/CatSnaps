package com.avsoftware.catsnaps.ui.breed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.avsoftware.catsnaps.R
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme

@Composable
fun BreedItem(
    breed: CatBreed,
    showPhotosClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
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
                )
            },

            supportingContent = {
                Text(
                    text = breed.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            },
            trailingContent = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
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
                            contentDescription = "Display cat pictures for ${breed.name}",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        )
    }
}

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