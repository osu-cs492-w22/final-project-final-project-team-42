package com.example.infinidnd.data

import com.squareup.moshi.Json

data class DamageTypeResults(
    @Json(name = "results") val items: List<DamageType>
)
