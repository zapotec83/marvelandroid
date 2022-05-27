package com.jorider.data.common.error

import android.util.Log
import retrofit2.HttpException
import java.net.UnknownHostException

class Error(val errorType: ErrorTypes, val message: String) {

    companion object {

        fun getError(throwable: Throwable): Error {
            Log.e(Error::class.java.simpleName, throwable.message + " -> " + throwable.javaClass.name, throwable)

            return when (throwable) {
                is HttpException -> {
                    val errorType = ErrorTypes.fromCode(throwable.code())
                    val errorMsg = MarvelWSErrorUtil.getErrorMsg(throwable)
                    Error(errorType, errorMsg)
                }
                is UnknownHostException -> {
                    Error(ErrorTypes.NO_INTERNET, ErrorTypes.NO_INTERNET.name)
                }
                else -> Error(ErrorTypes.UNKNOWN, ErrorTypes.UNKNOWN.name)
            }
        }
    }

    override fun toString(): String {
        return "$errorType, message='$message')"
    }
}