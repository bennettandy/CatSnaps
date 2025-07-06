package com.avsoftware.catsnaps.ui.images

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avsoftware.catsnaps.R
import com.avsoftware.domain.model.CatBreed
import com.avsoftware.domain.model.CatImage
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import com.avsoftware.catsnaps.ui.common.withSharedTransitionElement
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CatImages(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    selectedBreed: CatBreed,
    getPaginatedImages: (CatBreed) -> Flow<PagingData<CatImage>>,
    onImageClick: (CatImage) -> Unit = {},
) {

    val images = getPaginatedImages(selectedBreed).collectAsLazyPagingItems()

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        // TODO: Loading indicator
        // TODO: Error indicator
        // @andrew: any read interaction with images.loadState appears to disrupt the paginator
        // revisit when we have more time - Create a JIRA ticket

        val titleModifier = Modifier.withSharedTransitionElement(
            key = "text-${selectedBreed.id}",
            sharedTransitionScope,
            animatedVisibilityScope
        )

        val supportingModifier = Modifier.withSharedTransitionElement(
            key = "supporting-${selectedBreed.id}",
            sharedTransitionScope,
            animatedVisibilityScope
        )

        val gridModifier = Modifier.withSharedTransitionElement(
            key = "box-${selectedBreed.id}",
            sharedTransitionScope,
            animatedVisibilityScope
        )


        Text(
            modifier = titleModifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            text = selectedBreed.name,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = supportingModifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            text = selectedBreed.temperament,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = gridModifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                count = images.itemCount,
                key = images.itemKey { it.id },
            ) { index ->
                images[index]?.let {
                    CatImageItem(
                        catImage = it,
                        contentDescription = stringResource(R.string.cat_image_content_desc, selectedBreed.name),
                        onClick = { onImageClick(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun CatImageItem(
    catImage: CatImage,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(catImage.url)
                .crossfade(true)
                .placeholder(R.drawable.loading_cat)
                .error(R.drawable.error_image)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .aspectRatio(1f)
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@MultiThemePreview
@Composable
fun CatImagesPreview() {
    CatSnapsTheme {
        val mockBreed = CatBreed(
            name = "Siamese",
            description = "Elegant and vocal",
            id = "sms",
            temperament = "aloof"
        )
        val mockImages = flowOf(
            PagingData.from(
                listOf(
                    CatImage(
                        id = "1",
                        url = "https://example.com/cat1.jpg",
                        height = 128,
                        width = 128
                    ),
                    CatImage(
                        id = "2",
                        url = "https://example.com/cat2.jpg",
                        height = 128,
                        width = 128
                    ),
                    CatImage(
                        id = "3",
                        url = "https://example.com/cat3.jpg",
                        height = 128,
                        width = 128
                    )
                )
            )
        )
        CatImages(
            selectedBreed = mockBreed,
            getPaginatedImages = { mockImages },
            modifier = Modifier.fillMaxSize()
        )
    }
}