package com.jorider.domain.usecase.impl

import com.jorider.domain.model.Heroes
import com.jorider.domain.model.HeroesPaginationWrapper
import com.jorider.domain.repository.MarvelRepository
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MarvelUseCaseImplTest {

    private val mockMarvelRepository: MarvelRepository = mock(MarvelRepository::class.java)
    private val marvelUseCaseImpl = MarvelUseCaseImpl(mockMarvelRepository)

    @Test
    fun getHeroes_test_ok() {
        whenever(mockMarvelRepository.getHeroes(any(), any())).thenReturn(Single.fromCallable {
            HeroesPaginationWrapper(100, 1, arrayListOf())
        })

        val test1 = marvelUseCaseImpl.getHeroes(128238, 1111).test()
        val test2 = marvelUseCaseImpl.getHeroes(9997232, 991).test()
        val test3 = marvelUseCaseImpl.getHeroes(100, 0).test()
        test1.assertComplete().assertValue { it.offset == 100 }
        test2.assertComplete().assertValue { it.total == 1 }
        test3.assertComplete().assertValue { it.heroes.isEmpty() }

        test1.dispose()
        test2.dispose()
        test3.dispose()
    }

    @Test
    fun getHeroes_testRxJavaError() {
        val errorSingle = Single.error<HeroesPaginationWrapper>(RuntimeException())

        whenever(mockMarvelRepository.getHeroes(any(), any())).thenReturn(errorSingle)

        val test = marvelUseCaseImpl.getHeroes(128238, 1111).test()
        test.assertError(RuntimeException::class.java)

        test.dispose()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getHeroesDetailInfo_test_ok() {
        mockMarvelRepository.stub {
            onBlocking { getHeroesInfo(any()) }.doReturn(Heroes("123", "Spiderman", "image", "description"))
        }

        runTest {
            val heroes = marvelUseCaseImpl.getHeroesDetailInfo("1234")

            assert(heroes.id.contentEquals("123"))
            assert(heroes.name.contentEquals("Spiderman"))
            assert(heroes.image.contentEquals("image"))
            assert(heroes.description.contentEquals("description"))
        }
    }

    @Test(expected = Exception::class)
    fun getHeroesDetailInfo_test_exception() {
        mockMarvelRepository.stub {
            onBlocking { getHeroesInfo(any()) }.thenThrow(Exception(""))
        }

        runTest {
            marvelUseCaseImpl.getHeroesDetailInfo("1234")
        }
    }

}