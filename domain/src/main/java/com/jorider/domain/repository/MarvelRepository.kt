package com.jorider.domain.repository

import com.jorider.domain.model.Heroes
import com.jorider.domain.model.HeroesPaginationWrapper
import io.reactivex.Single

interface MarvelRepository {
    fun getHeroes(limit: Int, offset: Int): Single<HeroesPaginationWrapper>

    suspend fun getHeroesInfo(characterId: String): Heroes
}