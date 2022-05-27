package com.jorider.data.ws.model

import com.google.gson.annotations.SerializedName

data class MarvelResponseWS (
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: MarvelDataWS,
    @SerializedName("etag") val etag: String,
)