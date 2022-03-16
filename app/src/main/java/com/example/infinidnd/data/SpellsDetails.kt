package com.example.infinidnd.data

import java.io.Serializable
import com.squareup.moshi.FromJson

data class SpellsDetails(
    val name: String,
    val index: String,
    val url: String,
    val description: List<String>,
    val higherLvl: List<String>,
    val range: String,
    val components: List<String>,
    val material: String?,
    val ritual: Boolean,
    val duration: String,
    val concentration: Boolean,
    val castTime: String,
    val lvl: Int,
    val school: String,
    val classes: List<ClassesJson>,
    val subclasses: List<SubclassesJson>,
) : Serializable

data class SpellsJson(
    val name: String,
    val index: String,
    val url: String,
    val desc: List<String>,
    val higher_level: List<String>,
    val range: String,
    val components: List<String>,
    val material: String?,
    val ritual: Boolean,
    val duration: String,
    val concentration: Boolean,
    val casting_time: String,
    val level: Int,
    val school: SchoolJson,
    val classes: List<ClassesJson>,
    val subclasses: List<SubclassesJson>
)

data class SchoolJson(
    val name: String
)

data class ClassesJson(
    val name: String
)

data class SubclassesJson(
    val name: String
)

class SpellsJsonAdapter{
    @FromJson
    fun spellsFromJson(item: SpellsJson) = SpellsDetails(
        name = item.name,
        index = item.index,
        url = item.url,
        description = item.desc,
        higherLvl = item.higher_level,
        range = item.range,
        components = item.components,
        material = item.material,
        ritual = item.ritual,
        duration = item.duration,
        concentration = item.concentration,
        castTime = item.casting_time,
        lvl = item.level,
        school = item.school.name,
        classes = item.classes,
        subclasses = item.subclasses
    )
}