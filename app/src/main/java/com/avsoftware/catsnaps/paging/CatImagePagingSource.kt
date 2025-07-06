package com.avsoftware.catsnaps.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avsoftware.domain.model.CatBreed
import com.avsoftware.domain.model.CatImage
import com.avsoftware.domain.model.usecase.CatImagesByBreedUseCase
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.io.IOException

class CatImagePagingSource(
    private val useCase: CatImagesByBreedUseCase,
    private val breed: CatBreed,
    private val pageSize: Int
) : PagingSource<Int, CatImage>() {

    override fun getRefreshKey(state: PagingState<Int, CatImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatImage> {
        val page = params.key ?: 0
        return try {
            val images = useCase.getImages(breed, page, pageSize).first()

            Timber.d("Page: $page Got ${images.size} Images, 1st id ${images.first().id}")

            LoadResult.Page(
                data = images,
                // previous page unless we are already on page 0
                prevKey = if (page == 0) null else page - 1,
                // next page unless we have reached the end of the list
                nextKey = if (images.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(IOException("Failed to load images: ${e.message}", e))
        }
    }
}