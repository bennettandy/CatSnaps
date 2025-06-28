package com.avsoftware.catsnaps.ui.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.model.CatImage
import com.avsoftware.catsnaps.ui.common.MultiThemePreview
import com.avsoftware.catsnaps.ui.theme.CatSnapsTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun CatImages(
    modifier: Modifier = Modifier,
    selectedBreed: CatBreed,
    getPaginatedImages: (CatBreed) -> Flow<PagingData<CatImage>>
){

    val images: LazyPagingItems<CatImage> = getPaginatedImages(selectedBreed).collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2)) {
        items(
            count = images.itemCount,
            key = images.itemKey { it.id },

        ) { index ->
            val item = images[index]
            Column {
                Text(text = "${item?.url}")

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item?.url)
                            .crossfade(true)
                            .placeholder(android.R.drawable.progress_indeterminate_horizontal) // Loading
                            .error(android.R.drawable.ic_menu_gallery) // Error
                            .build(),
                        contentDescription = "cat image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 128.dp, height = 128.dp)
                            .padding(end = 16.dp)
                    )

            }
        }
    }
}

@MultiThemePreview
@Composable
fun CatImagesPreview(){
    CatSnapsTheme {
        //CatImages()
    }
}