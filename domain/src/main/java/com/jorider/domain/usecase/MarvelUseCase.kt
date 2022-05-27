package com.jorider.domain.usecase

import com.jorider.domain.model.Heroes
import com.jorider.domain.model.HeroesPaginationWrapper
import io.reactivex.Single

interface MarvelUseCase {

    suspend fun getHeroesDetailInfo(characterId: String): Heroes

    fun getHeroes(limit: Int, offset: Int): Single<HeroesPaginationWrapper>
}