package com.lnight.networkcompose.model.di

import com.lnight.networkcompose.model.data.remote.DogApi
import com.lnight.networkcompose.model.repository.DogRepository
import com.lnight.networkcompose.model.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
    object AppModule {

        @Singleton
        @Provides
        fun providePokemonRepository(
            api: DogApi
        ) = DogRepository(api)

        @Singleton
        @Provides
        fun providePokeApi(): DogApi {
            return Ktorfit(BASE_URL, HttpClient {
                install(ContentNegotiation) {
                    json(Json { isLenient = true; ignoreUnknownKeys = true })
                }
            }).create()
        }
    }