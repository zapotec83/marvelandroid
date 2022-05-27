package com.jorider.data.ws

import com.jorider.data.ws.impl.MarvelWSDataSourceImpl
import com.jorider.data.ws.model.MarvelDataWS
import com.jorider.data.ws.model.MarvelResponseWS
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.whenever

class MarvelWSDataSourceImplTest {

    private val mockMarvelApiService: MarvelAPIService = mock(MarvelAPIService::class.java)
    private var marvelWSDataSourceImplTest = MarvelWSDataSourceImpl(mockMarvelApiService)

    private val marvelResponseTestResponse = initMarvelResponse()

    private fun initMarvelResponse(): MarvelResponseWS {
        return MarvelResponseWS(200, "status", MarvelDataWS(0, 100, 100, 100, arrayListOf()), "etag")
    }

    @Test
    fun getHeroes_ok() {
        whenever(mockMarvelApiService.getCharacters(any(), any())).thenReturn(Single.fromCallable {
            marvelResponseTestResponse
        })

        val test = marvelWSDataSourceImplTest.getHeroes(100, 0)
            .test()

        test.assertComplete().assertValue { it.code == 200 }
        test.assertComplete().assertValue { it.etag == "etag" }
        test.assertComplete().assertValue { it.status == "status" }
        test.assertComplete().assertValue { it.data.results.isEmpty() }

        test.dispose()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getHeroesSuspend_ok() {
        mockMarvelApiService.stub {
            onBlocking { getCharactersSuspend(any()) }.doReturn(marvelResponseTestResponse)
        }

        runTest {
            val heroes = marvelWSDataSourceImplTest.getHeroesSuspend("1234")

            assert(heroes.code == 200)
            assert(heroes.etag == "etag")
            assert(heroes.status == "status")
            assert(heroes.data.results.isEmpty())
        }
    }
}