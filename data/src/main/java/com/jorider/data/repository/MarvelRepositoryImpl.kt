package com.jorider.data.repository

import com.jorider.data.ws.MarvelWSDataSource
import com.jorider.data.ws.model.MarvelDataWS
import com.jorider.data.ws.model.MarvelHeroesWS
import com.jorider.data.ws.model.MarvelThumbnailWS
import com.jorider.domain.model.Heroes
import com.jorider.domain.model.HeroesPaginationWrapper
import com.jorider.domain.repository.MarvelRepository
import io.reactivex.Single
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(private val marvelWSDataSource: MarvelWSDataSource) : MarvelRepository {

    override fun getHeroes(limit: Int, offset: Int): Single<HeroesPaginationWrapper> {
        return marvelWSDataSource.getHeroes(limit, offset)
            .map { heroesMapper(it.data) }
    }

    override suspend fun getHeroesInfo(characterId: String): Heroes {
        return extractHeroes(marvelWSDataSource.getHeroesSuspend(characterId).data.results.first())
    }

    private fun heroesMapper(heroesWS: MarvelDataWS): HeroesPaginationWrapper =
        with(heroesWS) {
            HeroesPaginationWrapper(
                offset,
                total,
                results
                    .map {
                        extractHeroes(it)
                    }
            )
        }

    private fun extractHeroes(marvelHeroesWS: MarvelHeroesWS): Heroes {
        return Heroes(
            marvelHeroesWS.id.toString(),
            marvelHeroesWS.name,
            getImagePath(marvelHeroesWS.marvelThumbnailWS),
            marvelHeroesWS.description
        )
    }

    private fun getImagePath(marvelThumbnailWS: MarvelThumbnailWS): String {
        val path =  if (marvelThumbnailWS.path.isNullOrEmpty()) "" else marvelThumbnailWS.path
        val extension = if (marvelThumbnailWS.extension.isNullOrEmpty()) "jpg" else marvelThumbnailWS.extension

        return "$path.$extension"
    }
}
