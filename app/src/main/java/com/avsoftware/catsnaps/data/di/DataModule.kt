package com.avsoftware.catsnaps.data.di

import com.avsoftware.catsnaps.BuildConfig
import com.avsoftware.catsnaps.data.CatImagesByBreedRetrofitUseCase
import com.avsoftware.catsnaps.data.GetBreedsRetrofitUseCase
import com.avsoftware.catsnaps.data.remote.AuthInterceptor
import com.avsoftware.catsnaps.data.remote.CatApiService
import com.avsoftware.domain.usecase.CatImagesByBreedUseCase
import com.avsoftware.domain.usecase.GetBreedsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/v1/"
    }

    @Provides
    @Singleton
    fun provideCatApi(retrofit: Retrofit): CatApiService =
        retrofit.create(CatApiService::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {

            // Enable HTTP BODY logging only in debug builds
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor(
        BuildConfig.CAT_API_KEY
    )

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideGetBreedsUseCase(catApiService: CatApiService): GetBreedsUseCase =
        GetBreedsRetrofitUseCase(catApiService)

    @Provides
    @Singleton
    fun provideCatImagesByBreedUseCase(catApiService: CatApiService): CatImagesByBreedUseCase =
        CatImagesByBreedRetrofitUseCase(catApiService)
}