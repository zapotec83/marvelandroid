package com.jorider.example.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jorider.data.common.Constants
import com.jorider.domain.model.Heroes
import com.jorider.domain.model.HeroesPaginationWrapper
import com.jorider.domain.usecase.MarvelUseCase
import com.jorider.example.core.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MarvelUseCase,
    @Named("scheduler_io") private val ioScheduler: Scheduler,
    @Named("scheduler_main") private val mainScheduler: Scheduler
) : ViewModel() {

    private val loadingData: AtomicBoolean = AtomicBoolean(false)
    private var currentOffset = 0
    private val currentHeroesList = mutableListOf<Heroes>()
    private val disposable = CompositeDisposable()

    val marvelComics: MutableLiveData<ResultWrapper<List<Heroes>>> = MutableLiveData()

    fun refreshHeroesList() {
        if (loadingData.compareAndSet(false, true)) {
            loadingData.set(true)
            useCase.getHeroes(Constants.PAGE_SIZE, currentOffset)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribeBy(
                    onError = {
                        marvelComics.value = ResultWrapper.Failure(it)
                        loadingData.set(false)
                    },
                    onSuccess = {
                        publishSuccess(it)
                        loadingData.set(false)
                    }
                ).addTo(disposable)
        }
    }

    private fun publishSuccess(paginationWrapper: HeroesPaginationWrapper) {
        currentHeroesList.addAll(paginationWrapper.heroes)
        marvelComics.value = ResultWrapper.Success(currentHeroesList.toList())
        currentOffset = paginationWrapper.offset + Constants.PAGE_SIZE
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}