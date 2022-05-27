package com.jorider.example.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jorider.example.MainCoroutineRule
import com.jorider.domain.model.Heroes
import com.jorider.domain.usecase.MarvelUseCase
import com.jorider.example.core.ResultWrapper
import com.jorider.example.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeroesInfoViewModelTest {

    private val mockUseCase: MarvelUseCase = mock(MarvelUseCase::class.java)

    private lateinit var viewModel: HeroesInfoViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpViewModel() {
        viewModel = HeroesInfoViewModel(mockUseCase, Dispatchers.Main)
    }

    @Test
    fun getHeroesDetailInformation_success() {
        val heroes = Heroes("1234", "Name", "image", "description")
        mockUseCase.stub {
            onBlocking { getHeroesDetailInfo("") }.thenReturn(heroes)
        }

        runTest {
            mockUseCase.getHeroesDetailInfo("")
        }
        val value = viewModel.getHeroesDetailInformation("").getOrAwaitValue()

        assert(value is ResultWrapper.Success)
    }

    @Test
    fun getHeroesDetailInformation_fail() {
        mockUseCase.stub {
            onBlocking { getHeroesDetailInfo(any()) }.thenThrow()
        }

        val value = viewModel.getHeroesDetailInformation("").getOrAwaitValue()

        runTest {
            mockUseCase.getHeroesDetailInfo("1234")
        }

        assert(value is ResultWrapper.Failure)
    }

}