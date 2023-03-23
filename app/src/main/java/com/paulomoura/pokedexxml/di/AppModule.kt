package com.paulomoura.pokedexxml.di

import com.paulomoura.pokedexxml.model.datasource.service.PokemonService
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
object AppModule {

    private fun getOkHttpClient() = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build()

    @Singleton
    @Provides
    fun provideHttpClient() = Retrofit
        .Builder()
        .baseUrl("http://192.168.15.22:8000")
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providePokemonService(retrofit: Retrofit) = retrofit.create(PokemonService::class.java)
}