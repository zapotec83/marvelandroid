package com.jorider.data.ws.model

import com.google.gson.annotations.SerializedName

data class MarvelDataWS(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<MarvelHeroesWS>
)
