package com.jorider.example.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ViewModelComponent::class)
object DispatchersProvider {

    @Provides
    @Named("scheduler_io")
    fun providesIO(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named("scheduler_main")
    fun providesMain(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    fun mainContext(): CoroutineContext {
        return Dispatchers.IO
    }
}