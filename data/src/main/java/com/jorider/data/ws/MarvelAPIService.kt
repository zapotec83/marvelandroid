package com.jorider.data.ws

import com.jorider.data.ws.model.MarvelResponseWS
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelAPIService {

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharactersSuspend(@Path("characterId") characterId: String): MarvelResponseWS

    @GET("/v1/public/characters")
    fun getCharacters(@Query("limit") limit: Int, @Query("offset") offset: Int?): Single<MarvelResponseWS>
}