package com.jorider.domain.model

data class HeroesPaginationWrapper(
    val offset: Int,
    val total: Int,
    val heroes: List<Heroes>
)
