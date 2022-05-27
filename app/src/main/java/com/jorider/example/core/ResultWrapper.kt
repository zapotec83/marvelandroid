package com.jorider.example.core

import com.jorider.data.common.error.Error

sealed class ResultWrapper<T> {
    open class Success<T>(val data: T) : ResultWrapper<T>()
    open class Failure<T>(val throwable: Throwable) : ResultWrapper<T>()

    inline fun doFail(callback: (value: Error) -> Unit) {
        if (this is Failure<T>) {
            callback(Error.getError(throwable))
        }
    }

    inline fun doSuccess(callback: (success: T) -> Unit) {
        if (this is Success<T>) {
            callback(data)
        }
    }

}

