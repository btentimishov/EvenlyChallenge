package com.baktyiar.evenlychallenge.data.di

import com.baktyiar.evenlychallenge.data.repository.PoiRepositoryImpl
import com.baktyiar.evenlychallenge.domain.repository.PoiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPoiRepository(
        poiRepositoryImpl: PoiRepositoryImpl
    ): PoiRepository
}
