package com.jorider.data.di

import com.jorider.data.repository.MarvelRepositoryImpl
import com.jorider.domain.repository.MarvelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsRepository(impl: MarvelRepositoryImpl): MarvelRepository
}