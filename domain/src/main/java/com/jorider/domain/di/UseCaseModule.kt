package com.jorider.domain.di

import com.jorider.domain.usecase.MarvelUseCase
import com.jorider.domain.usecase.impl.MarvelUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindUseCaseMarvel(impl: MarvelUseCaseImpl): MarvelUseCase
}