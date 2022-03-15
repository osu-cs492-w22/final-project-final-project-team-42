package com.example.infinidnd.data

import com.squareup.moshi.Json
import java.io.Serializable

data class AllData(
    @Json(name = "name") val name: String,
    @Json(name = "index") val index: String,
    @Json(name = "url") val url: String,
) : Serializable
