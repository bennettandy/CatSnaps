package com.avsoftware.catsnaps.domain.model

import java.util.UUID

data class CatImage(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int,
//    val pageNumber: Int,
//    val uuid: UUID
)
