package com.example.infinidnd.data

import java.io.Serializable
import com.squareup.moshi.FromJson

data class MonstersDetails (
    val name: String,
    val index: String,
    val url: String,
    val size: String,
    val type: String,
    val subtype: String?,
    val alignment: String,
    val ac: Int,
    val hp: Int,
    val hd: String,
    val spdWalk: String?,
    val spdSwim: String?,
    val spdFly: String?,
    val spdClimb: String?,
    val str: Int,
    val dex: Int,
    val con: Int,
    val int: Int,
    val wis: Int,
    val cha: Int,
    val proficiencies: List<ProficienciesJson>,
    val dmgVulns: List<String>,
    val dmgResist: List<String>,
    val dmgImmune: List<String>,
    val condImmune: List<ConditionJson>?,
    val languages: String,
    val cr: Float,
    val sa: List<SpecialAbilitiesJson>?,
    val act: List<ActionJson>,
    val lAct: List<LegendaryActionsJson>?,

) : Serializable

data class MonsterJson (
    val name: String,
    val index: String,
    val url: String,
    val size: String,
    val type: String,
    val subtype: String?,
    val alignment: String,
    val armor_class: Int,
    val hit_points: Int,
    val hit_dice: String,
    val speed: SpeedJson,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val proficiencies: List<ProficienciesJson>,
    val damage_vulnerabilities: List<String>,
    val damage_resistances: List<String>,
    val damage_immunities: List<String>,
    val condition_immunities: List<ConditionJson>?,
    val languages: String,
    val challenge_rating: Float,
    val special_abilities: List<SpecialAbilitiesJson>?,
    val actions: List<ActionJson>,
    val legendary_actions: List<LegendaryActionsJson>?,

)

data class SpeedJson (
    val walk: String?,
    val swim: String?,
    val fly: String?,
    val climb: String?
)

data class ProficienciesJson(
    val value: Int,
    val proficiency: ProficiencyJson
)

data class ProficiencyJson(
    val name: String?
)

data class ConditionJson(
    val name: String?
)

data class SpecialAbilitiesJson(
    val name: String?,
    val desc: String?
)

data class ActionJson(
    val name: String,
    val desc: String
)

data class LegendaryActionsJson(
    val name: String,
    val desc: String
)

class MonstersJsonAdapter{
    @FromJson
    fun monsterFromJson(item: MonsterJson) = MonstersDetails(
        name = item.name,
        index = item.index,
        url = item.url,
        size = item.size,
        type = item.type,
        subtype = item.subtype,
        alignment = item.alignment,
        ac = item.armor_class,
        hp = item.hit_points,
        hd = item.hit_dice,
        spdClimb = item.speed.climb,
        spdWalk = item.speed.walk,
        spdFly = item.speed.fly,
        spdSwim = item.speed.swim,
        str = item.strength,
        dex = item.dexterity,
        con = item.constitution,
        int = item.intelligence,
        wis = item.wisdom,
        cha = item.charisma,
        proficiencies =item.proficiencies,
        dmgVulns = item.damage_vulnerabilities,
        dmgImmune = item.damage_immunities,
        dmgResist = item.damage_resistances,
        condImmune = item.condition_immunities,
        languages = item.languages,
        cr = item.challenge_rating,
        sa = item.special_abilities,
        act = item.actions,
        lAct = item.legendary_actions
    )
}