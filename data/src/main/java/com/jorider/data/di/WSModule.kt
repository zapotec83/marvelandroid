package com.jorider.data.di

import com.jorider.data.ws.MarvelWSDataSource
import com.jorider.data.ws.impl.MarvelWSDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WSModule {

    @Binds
    abstract fun bindsWsClient(impl: MarvelWSDataSourceImpl): MarvelWSDataSource

}