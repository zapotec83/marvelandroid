package com.jorider.data.ws.model

import com.google.gson.annotations.SerializedName

data class MarvelThumbnailWS(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)