package com.jorider.data.common.error

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class ErrorTest {

    @Test
    fun getErrorType_exception_no_message() {
        val exception = Exception("")
        val error = Error.getError(exception)

        assert(error.errorType == ErrorTypes.UNKNOWN)
        assert(error.message == ErrorTypes.UNKNOWN.name)
    }

    @Test
    fun getErrorType_exception_no_internet() {
        val exception = UnknownHostException("")
        val error = Error.getError(exception)

        assert(error.errorType == ErrorTypes.NO_INTERNET)
        assert(error.message == ErrorTypes.NO_INTERNET.name)
    }

    @Test
    fun getErrorType_exception_401_AUTH_ERROR() {
        val test: Response<String> = Response.error(401, "".toResponseBody())

        val exception = HttpException(test)
        val error = Error.getError(exception)

        assert(error.errorType == ErrorTypes.AUTH_ERROR)
    }

    @Test
    fun getErrorType_exception_403_FORBIDDEN() {
        val test: Response<String> = Response.error(403, "".toResponseBody())

        val exception = HttpException(test)
        val error = Error.getError(exception)

        assert(error.errorType == ErrorTypes.FORBIDDEN)
    }

    @Test
    fun getErrorType_exception_405_NOT_ALLOWED() {
        val test: Response<String> = Response.error(405, "".toResponseBody())

        val exception = HttpException(test)
        val error = Error.getError(exception)

        assert(error.errorType == ErrorTypes.NOT_ALLOWED)
    }

    @Test
    fun getErrorType_exception_409_AUTH_ERROR() {
        val test: Response<String> = Response.error(409, "".toResponseBody())

        val exception = HttpException(test)
        val error = Error.getError(exception)

        assert(error.errorType == ErrorTypes.AUTH_ERROR)
    }

    @Test
    fun getErrorType_exception_other_code_UNKNOWN() {
        val test: Response<String> = Response.error(400, "".toResponseBody())

        val exception = HttpException(test)
        val error = Error.getError(exception)

        assert(error.errorType == ErrorTypes.UNKNOWN)

        val test2: Response<String> = Response.error(500, "".toResponseBody())

        val exception2 = HttpException(test2)
        val error2 = Error.getError(exception2)

        assert(error2.errorType == ErrorTypes.UNKNOWN)
    }
}