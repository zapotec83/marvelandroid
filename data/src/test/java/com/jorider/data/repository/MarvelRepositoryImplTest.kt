package com.jorider.data.repository

import com.jorider.data.ws.MarvelWSDataSource
import com.jorider.data.ws.model.MarvelDataWS
import com.jorider.data.ws.model.MarvelHeroesWS
import com.jorider.data.ws.model.MarvelResponseWS
import com.jorider.data.ws.model.MarvelThumbnailWS
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.stub
import org.mockito.kotlin.whenever
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MarvelRepositoryImplTest {

    private val mockMarvelWSDataSource: MarvelWSDataSource = mock(MarvelWSDataSource::class.java)
    private val marvelRepositoryImpl = MarvelRepositoryImpl(mockMarvelWSDataSource)

    private val marvelResponseWS: MarvelResponseWS = initResponse()

    private fun initResponse(): MarvelResponseWS {
        val dataList = mutableListOf<MarvelHeroesWS>()
        dataList.add(MarvelHeroesWS(1111, "Name1", "Description1", MarvelThumbnailWS("image1", "jpg")))
        dataList.add(MarvelHeroesWS(2222, "Name2", "Description3", MarvelThumbnailWS("image2", "png")))

        return MarvelResponseWS(200, "status", MarvelDataWS(0, 100, 100, 100, dataList), "etag")
    }

    @Test
    fun getHeroes_heroes_ok() {
        whenever(mockMarvelWSDataSource.getHeroes(any(), any())).thenReturn(Single.fromCallable {
            marvelResponseWS
        })

        val testGetHeroes = marvelRepositoryImpl.getHeroes(2, 5).test()

        testGetHeroes.assertComplete()
        testGetHeroes.assertValue { it.heroes.size == 2 }
        testGetHeroes.assertValue { it.total == 100 }
        testGetHeroes.assertValue { it.offset == 0 }
        testGetHeroes.assertValue { it.heroes[1].name == "Name2" }
        testGetHeroes.assertValue { it.heroes[0].image == "image1.jpg" }
        testGetHeroes.assertValue { it.heroes[1].image == "image2.png" }
        testGetHeroes.assertValue { it.heroes[1].description == "Description3" }

        testGetHeroes.dispose()
    }

    @Test
    fun getHeroes_heroes_error() {
        whenever(mockMarvelWSDataSource.getHeroes(any(), any())).thenReturn(Single.error {
            Exception("")
        })

        val testGetHeroes = marvelRepositoryImpl.getHeroes(2, 5).test()

        testGetHeroes.assertNotComplete()
        testGetHeroes.assertError(Exception::class.java)

        testGetHeroes.dispose()
    }

    @Test
    fun getHeroesInfo_ok() {
        mockMarvelWSDataSource.stub {
            onBlocking { getHeroesSuspend(any()) }.thenReturn(marvelResponseWS)
        }

        runTest {
            val heroesResponse = marvelRepositoryImpl.getHeroesInfo("1234")

            assert(heroesResponse.image == "image1.jpg")
            assert(heroesResponse.description == "Description1")
            assert(heroesResponse.name == "Name1")

        }
    }

    @Test(expected = Exception::class)
    fun getHeroesInfo_exception() {
        mockMarvelWSDataSource.stub {
            onBlocking { getHeroesSuspend(any()) }.thenThrow(Exception(""))
        }

        runTest {
            marvelRepositoryImpl.getHeroesInfo("1234")
        }
    }
}