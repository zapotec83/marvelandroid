package com.jorider.data.common.error

enum class ErrorTypes {
    AUTH_ERROR,
    NO_INTERNET,
    FORBIDDEN,
    NOT_ALLOWED,
    UNKNOWN;

    companion object {
        fun fromCode(code: Int): ErrorTypes {
            return when (code) {
                401 -> AUTH_ERROR
                403 -> FORBIDDEN
                405 -> NOT_ALLOWED
                409 -> AUTH_ERROR
                else -> UNKNOWN
            }
        }
    }
}