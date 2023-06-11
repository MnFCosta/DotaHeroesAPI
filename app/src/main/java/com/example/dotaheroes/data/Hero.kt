package com.example.dotaheroes.data

import com.squareup.moshi.Json

data class Hero(
    val id: Int,
    @Json(name="localized_name") val name: String,
    val img: String,
)
