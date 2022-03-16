package com.example.infinidnd.data

import com.squareup.moshi.FromJson
import java.io.Serializable

data class FeatDetails(
    val name: String,
    val desc: List<String>,
    val url: String,
    val index: String,
    val abilityScore: String,
    val minScore: Int,
) : Serializable

data class Feat(
    val name: String,
    val desc: List<String>,
    val url: String,
    val index: String,
    val prerequisites: List<Prereqs>,

    )

data class Prereqs(
    val ability_score: AbilityScore,
    val minimum_score: Int,
)
data class AbilityScore(
    val index: String,
    val name: String,
    val url: String,
)

class FeatListJsonAdapter {
    @FromJson
    fun FeatFromJson(feat: Feat) = FeatDetails(
        name = feat.name,
        desc = feat.desc,
        url = feat.url,
        index = feat.index,
        abilityScore = feat.prerequisites[0].ability_score.name,
        minScore = feat.prerequisites[0].minimum_score,
    )
}

