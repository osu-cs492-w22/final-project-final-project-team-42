package com.example.infinidnd.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import java.io.Serializable

data class MagicItemDetails(
    val name: String,
    val desc: List<String>,
    val url: String,
    val index: String,
    val equipmentCategory: String,
) : Serializable

data class MagicItem(
    val name: String,
    val desc: List<String>,
    val url: String,
    val index: String,
    val equipment_category: Equipment_Category,

    )

data class Equipment_Category(
    val name: String,
    val url: String,
    val index: String,
)

class MagicItemJsonAdapter {
    @FromJson
    fun FeatFromJson(item: MagicItem) = MagicItemDetails(
        name = item.name,
        desc = item.desc,
        url = item.url,
        index = item.index,
        equipmentCategory = item.equipment_category.name,

    )
}
