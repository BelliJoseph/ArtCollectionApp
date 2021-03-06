package com.example.artcollectionapp.di

import com.example.artcollectionapp.repository.ArtRepository
import com.example.artcollectionapp.repository.ArtRepositoryImpl
import com.example.artcollectionapp.rest.ArtCollectionAPI
import com.example.artcollectionapp.viewModel.ArtViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InjectionModule {

    @Provides
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesServiceAPI(okHttpClient: OkHttpClient, moshi: Moshi) =
        Retrofit.Builder()
            .baseUrl(ArtCollectionAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(ArtCollectionAPI::class.java)

    @Provides
    fun providesRepository(artCollectionAPI: ArtCollectionAPI): ArtRepository =
        ArtRepositoryImpl(artCollectionAPI)

    @Provides
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providesViewModel(
        artRepository: ArtRepository,
        dispatcher: CoroutineDispatcher
    ): ArtViewModel = ArtViewModel(artRepository, dispatcher)

}