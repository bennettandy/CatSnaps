package com.avsoftware.catsnaps.ui.breed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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

@Composable
fun BreedItem(
    breed: CatBreed,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth(),
            //.clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = breed.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
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
                        .padding(8.dp), // Add padding for touch target
                    contentAlignment = Alignment.Center // Center the icon vertically and horizontally
                ) {
                    IconButton(
                        onClick = onClick, // Use the new callback
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), // Subtle background
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp) // Increase touch area
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_camera_roll_24),
                            contentDescription = "View cat pictures",
                            tint = MaterialTheme.colorScheme.primary, // Use primary color for emphasis
                            modifier = Modifier.size(28.dp) // Slightly larger icon
                        )
                    }
                }
            }
        )
    }
}