package com.jorider.domain.usecase.impl

import com.jorider.domain.model.Heroes
import com.jorider.domain.model.HeroesPaginationWrapper
import com.jorider.domain.repository.MarvelRepository
import com.jorider.domain.usecase.MarvelUseCase
import io.reactivex.Single
import javax.inject.Inject

class MarvelUseCaseImpl @Inject constructor(private val repository: MarvelRepository): MarvelUseCase {

    override fun getHeroes(limit: Int, offset: Int): Single<HeroesPaginationWrapper> = repository.getHeroes(limit, offset)

    override suspend fun getHeroesDetailInfo(characterId: String): Heroes = repository.getHeroesInfo(characterId)

}