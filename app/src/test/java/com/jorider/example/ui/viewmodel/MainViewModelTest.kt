package com.jorider.example.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jorider.domain.model.Heroes
import com.jorider.domain.model.HeroesPaginationWrapper
import com.jorider.domain.usecase.MarvelUseCase
import com.jorider.example.RxSchedulerRule
import com.jorider.example.core.ResultWrapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val mockUseCase: MarvelUseCase = mock(MarvelUseCase::class.java)

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var rxSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpViewModel() {
        viewModel = MainViewModel(mockUseCase, Schedulers.io(), AndroidSchedulers.mainThread())
    }

    @Test
    fun getMarvelComics_ok() {
        val heroesWrapper = HeroesPaginationWrapper(100, 1, arrayListOf())

        whenever(mockUseCase.getHeroes(any(), any())).thenReturn(Single.fromCallable {
            heroesWrapper
        })
        var value: ResultWrapper<List<Heroes>>? = null

        viewModel.marvelComics.observeForever {
            value = it
        }
        assert(value == null)
        viewModel.refreshHeroesList()

        assert(value!! is ResultWrapper.Success)
    }

    @Test
    fun getMarvelComics_fail() {
        whenever(mockUseCase.getHeroes(any(), any())).thenReturn(Single.error {
            Throwable()
        })
        var value: ResultWrapper<List<Heroes>>? = null

        viewModel.marvelComics.observeForever {
            value = it
        }
        assert(value == null)
        viewModel.refreshHeroesList()

        assert(value!! is ResultWrapper.Failure)
    }

}