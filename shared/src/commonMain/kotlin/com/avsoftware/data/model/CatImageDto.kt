package com.avsoftware.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CatImageDto(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
