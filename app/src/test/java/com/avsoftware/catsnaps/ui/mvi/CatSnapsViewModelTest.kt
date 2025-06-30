package com.avsoftware.catsnaps.ui.mvi

import com.avsoftware.catsnaps.domain.model.CatBreed
import com.avsoftware.catsnaps.domain.usecase.CatImagesByBreedUseCase
import com.avsoftware.catsnaps.domain.usecase.GetBreedsUseCase
import com.avsoftware.catsnaps.ui.common.LoadableList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.orbitmvi.orbit.test.test

class CatSnapsViewModelTest {

    private val getBreedsUseCase: GetBreedsUseCase = mockk()
    private val getImagesUseCase: CatImagesByBreedUseCase = mockk()

    @Test
    fun `test expected initial Ui State`() = runTest {

        // given
        val sut = CatSnapsViewModel(
            getBreedsUseCase = getBreedsUseCase,
            catImagesByBreedUseCase = getImagesUseCase
        )

        // when
        sut.test(testScope = this, initialState =  CatSnapsUiState.default) {
            // Expect only the initial UI State
            expectState { CatSnapsUiState.default }
        }

        // then
        coVerify(exactly = 0) { getBreedsUseCase.clearCache() }
        coVerify(exactly = 0) { getBreedsUseCase.getBreeds("") }
        confirmVerified(getBreedsUseCase)

        coVerify(exactly = 0) { getImagesUseCase.getImages(any(), any(), any()) }
        confirmVerified(getImagesUseCase)
    }

    @Test
    fun `test load breeds - success`() = runTest {
        // given
        coEvery { getBreedsUseCase.clearCache() } returns Unit
        coEvery { getBreedsUseCase.getBreeds("") } returns flowOf(catBreeds)

        val sut = CatSnapsViewModel(
            getBreedsUseCase = getBreedsUseCase,
            catImagesByBreedUseCase = getImagesUseCase
        )

        // when
        sut.test(testScope = this, initialState =  CatSnapsUiState.default) {
            // Initial UI State
            expectState { CatSnapsUiState.default }

            // Trigger loading of Cat Breeds
            sut.handleIntent(CatSnapsIntent.ReloadBreeds)

            // Expect updated UI State with breed list
            val successState = CatSnapsUiState.default.copy(
                catBreeds = LoadableList.Success(data = catBreeds)
            )
            expectState { successState }
        }

        // then
        coVerify(exactly = 1) { getBreedsUseCase.clearCache() }
        coVerify(exactly = 1) { getBreedsUseCase.getBreeds("") }
        confirmVerified(getBreedsUseCase)

        coVerify(exactly = 0) { getImagesUseCase.getImages(any(), any(), any()) }
        confirmVerified(getImagesUseCase)
    }


}

val catBreeds: List<CatBreed> = listOf(
    CatBreed(
        id = "abys",
        name = "Abyssinian",
        description = "The Abyssinian is a slender, elegant cat with large ears and a playful nature.",
        temperament = "Active, playful, affectionate"
    ),
    CatBreed(
        id = "siam",
        name = "Siamese",
        description = "Siamese cats are vocal, social, and have striking blue almond-shaped eyes.",
        temperament = "Vocal, social, intelligent"
    ),
    CatBreed(
        id = "main",
        name = "Maine Coon",
        description = "Maine Coons are large, friendly cats with tufted ears and bushy tails.",
        temperament = "Gentle, sociable, affectionate"
    ),
    CatBreed(
        id = "pers",
        name = "Persian",
        description = "Persians have long, thick fur and a calm, quiet demeanor.",
        temperament = "Quiet, gentle, affectionate"
    ),
    CatBreed(
        id = "beng",
        name = "Bengal",
        description = "Bengals have a wild appearance with spotted coats and an energetic personality.",
        temperament = "Energetic, curious, playful"
    )
)