package com.paulomoura.pokedexxml.di

import com.paulomoura.pokedexxml.model.datasource.service.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient() = Retrofit
        .Builder()
        .baseUrl("http://192.168.15.2:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providePokemonService(retrofit: Retrofit) = retrofit.create(PokemonService::class.java)
}