package com.baktyiar.evenlychallenge.data.di

import com.baktyiar.evenlychallenge.BuildConfig
import com.baktyiar.evenlychallenge.data.api.FoursquareApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val token = BuildConfig.FOURSQUARE_TOKEN
        return Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/v3/")
            .client(
                client.newBuilder().addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", token)
                        .build()
                    chain.proceed(request)
                }.build()
            )
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
    }

    @Provides
    @Singleton
    fun provideFoursquareApi(retrofit: Retrofit): FoursquareApi =
        retrofit.create(FoursquareApi::class.java)
}