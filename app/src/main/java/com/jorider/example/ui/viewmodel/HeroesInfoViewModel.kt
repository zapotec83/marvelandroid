package com.jorider.example.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorider.domain.model.Heroes
import com.jorider.domain.usecase.MarvelUseCase
import com.jorider.example.core.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HeroesInfoViewModel @Inject constructor(private val useCase: MarvelUseCase, private val ioDispatcher: CoroutineContext) : ViewModel() {

    fun getHeroesDetailInformation(characterId: String): LiveData<ResultWrapper<Heroes>> {
        val liveDataHeroes = MutableLiveData<ResultWrapper<Heroes>>()

        val handler = CoroutineExceptionHandler { _, throwable ->
            liveDataHeroes.value = ResultWrapper.Failure(throwable)
        }

        viewModelScope.launch(handler) {
            withContext(ioDispatcher) {
                liveDataHeroes.postValue(ResultWrapper.Success(useCase.getHeroesDetailInfo(characterId)))
            }
        }

        return liveDataHeroes
    }
}