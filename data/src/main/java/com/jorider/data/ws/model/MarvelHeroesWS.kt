package com.jorider.data.ws.model

import com.google.gson.annotations.SerializedName

data class MarvelHeroesWS(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail") val marvelThumbnailWS: MarvelThumbnailWS
)