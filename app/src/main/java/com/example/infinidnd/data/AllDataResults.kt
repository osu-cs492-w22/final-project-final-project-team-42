package com.example.infinidnd.data

import com.squareup.moshi.Json

data class AllDataResults(
    @Json(name = "results") val items: List<AllData>
)
