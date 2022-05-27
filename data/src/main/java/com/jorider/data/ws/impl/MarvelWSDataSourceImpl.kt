package com.jorider.data.ws.impl

import com.jorider.data.ws.MarvelAPIService
import com.jorider.data.ws.MarvelWSDataSource
import com.jorider.data.ws.model.MarvelResponseWS
import io.reactivex.Single
import javax.inject.Inject

class MarvelWSDataSourceImpl @Inject constructor(private val marvelAPIService: MarvelAPIService): MarvelWSDataSource {

    override suspend fun getHeroesSuspend(characterId: String): MarvelResponseWS {
        return marvelAPIService.getCharactersSuspend(characterId)
    }

    override fun getHeroes(limit: Int, offset: Int): Single<MarvelResponseWS> {
        return marvelAPIService.getCharacters(limit, offset)
    }
}