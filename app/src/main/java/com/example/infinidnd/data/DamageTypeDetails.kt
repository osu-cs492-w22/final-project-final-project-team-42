package com.example.infinidnd.data

import com.squareup.moshi.Json
import java.io.Serializable

data class DamageTypeDetails(
    @Json(name = "name") val name: String,
    @Json(name = "desc") val desc: List<String>,
    @Json(name = "url") val url: String,
    @Json(name = "index") val index: String,
) : Serializable
