package com.avsoftware.catsnaps.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.avsoftware.catsnaps.R
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme

@Composable
fun RetryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.secondary
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = stringResource(R.string.breed_error_retry_btn),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@MultiThemePreview
@Composable
fun BreedSearchPreview() {
    CatSnapsTheme {
        Column {
            RetryButton(
                onClick = {}
            )
        }
    }
}