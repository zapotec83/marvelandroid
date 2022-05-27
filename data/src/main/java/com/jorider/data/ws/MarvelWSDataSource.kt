package com.jorider.data.ws

import com.jorider.data.ws.model.MarvelResponseWS
import io.reactivex.Single

interface MarvelWSDataSource {

    suspend fun getHeroesSuspend(characterId: String): MarvelResponseWS

    fun getHeroes(limit: Int, offset: Int): Single<MarvelResponseWS>
}