package com.example.infinidnd.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import java.io.Serializable

data class EquipmentDetails (
    val name: String,
    val weaponCat: String,
    val weaponRange: String,
    val costNum: Int,
    val costType: String,
    val damageDice: String,
    val dmgType: String,
    val rngNorm: Int,
    val rngLong: Int?,
    val property: String?,
    ) : Serializable

data class EquipmentJson(
    val name: String,
    val weapon_category: String,
    val weapon_range: String,
    val cost: CostJson,
    val damage: DmgJson,
    val range: RangeJson,
    val properties: List<PropertiesJson>
)

data class CostJson(
    val quantity: Int,
    val unit: String
)

data class DmgJson(
    val damage_dice: String,
    val damage_type: DmgTypeJson
)

data class DmgTypeJson(
    val name: String
)

data class RangeJson(
    val normal: Int,
    val long: Int?
)

data class PropertiesJson(
    val name: String
)

class EquipmentJsonAdapter {
    @FromJson
    fun equipmentFromJson(item: EquipmentJson) = EquipmentDetails(
        name = item.name,
        weaponCat = item.weapon_category,
        weaponRange = item.weapon_range,
        costNum = item.cost.quantity,
        costType = item.cost.unit,
        damageDice = item.damage.damage_dice,
        dmgType = item.damage.damage_type.name,
        rngNorm=item.range.normal,
        rngLong = item.range.long,
        property=item.properties[0].name


    )
}