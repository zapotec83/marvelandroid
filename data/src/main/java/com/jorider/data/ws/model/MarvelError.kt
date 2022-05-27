package com.jorider.data.ws.model

import com.google.gson.annotations.SerializedName

data class MarvelError(
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)