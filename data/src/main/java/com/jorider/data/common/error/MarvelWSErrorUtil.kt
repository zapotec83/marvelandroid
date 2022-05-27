package com.jorider.data.common.error

import com.google.gson.Gson
import com.jorider.data.ws.model.MarvelError
import retrofit2.HttpException

object MarvelWSErrorUtil {

    fun getErrorMsg(throwable: HttpException): String {
        return try {
            var message = throwable.message()

            val marvelAPIError = getMarvelApiMessage(throwable)
            marvelAPIError?.let {
                message = marvelAPIError
            }
            message
        } catch (t: Throwable) {
            throwable.message()
        }
    }

    private fun getMarvelApiMessage(throwable: HttpException): String? {
        val responseString = throwable.response()?.errorBody()?.string()
        return if (!responseString.isNullOrEmpty()) {
            val marvelError = Gson().fromJson(responseString, MarvelError::class.java)
            marvelError.message
        } else {
            null
        }
    }

}