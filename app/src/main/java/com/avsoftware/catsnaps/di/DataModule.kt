package com.avsoftware.catsnaps.di

import com.avsoftware.catsnaps.BuildConfig
import com.avsoftware.data.CatClient
import com.avsoftware.data.CatImagesByBreedUseCaseImpl
import com.avsoftware.data.GetBreedsUseCaseImpl
import com.avsoftware.data.createHttpClient
import com.avsoftware.domain.model.usecase.CatImagesByBreedUseCase
import com.avsoftware.domain.model.usecase.GetBreedsUseCase
import io.ktor.client.HttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

//    companion object {
//        private const val BASE_URL = "https://api.thecatapi.com/v1/"
//    }

    @Provides
    fun provideHttpClient(): HttpClient = createHttpClient(OkHttp.create(), BuildConfig.CAT_API_KEY)

    @Provides
    @Singleton
    fun provideCatApi(httpClient: HttpClient): CatClient =
        CatClient(httpClient)

    @Provides
    @Singleton
    fun provideGetBreedsUseCase(catApiService: CatClient): GetBreedsUseCase =
        GetBreedsUseCaseImpl(catApiService)

    @Provides
    @Singleton
    fun provideCatImagesByBreedUseCase(catApiService: CatClient): CatImagesByBreedUseCase =
        CatImagesByBreedUseCaseImpl(catApiService)
}