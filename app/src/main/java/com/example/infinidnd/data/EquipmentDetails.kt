package com.example.infinidnd.data

import com.squareup.moshi.FromJson
import java.io.Serializable

data class EquipmentDetails(
    val name: String,
    val index: String,
    val url: String,
    val equipmentCat: String,
    val gearCat: String?,
    val toolCat: String?,
    val vehicleCat: String?,
    val weaponCat: String?,
    val weaponRange: String?,
    val costNum: Int,
    val costType: String,
    val damageDice: String?,
    val dmgType: String?,
    val rngNorm: Int?,
    val rngLong: Int?,
    val description: List<String>?
    ) : Serializable

data class EquipmentJson(
    val name: String,
    val index: String,
    val url: String,
    val equipment_category: EquipmentCatJson,
    val gear_category: GearCatJson?,
    val tool_category: String?,
    val vehicle_category: String?,
    val weapon_category: String?,
    val weapon_range: String?,
    val cost: CostJson,
    val damage: DmgJson?,
    val range: RangeJson?,
    val desc: List<String>?
)

data class EquipmentCatJson(
    val name: String
)

data class GearCatJson(
    val name: String?
)

data class CostJson(
    val quantity: Int,
    val unit: String
)

data class DmgJson(
    val damage_dice: String?,
    val damage_type: DmgTypeJson?
)

data class DmgTypeJson(
    val name: String?
)

data class RangeJson(
    val normal: Int?,
    val long: Int?
)

class EquipmentJsonAdapter {
    @FromJson
    fun equipmentFromJson(item: EquipmentJson) = EquipmentDetails(
        name = item.name,
        index = item.index,
        url = item.url,
        equipmentCat = item.equipment_category.name,
        gearCat = item.gear_category?.name,
        toolCat = item.tool_category,
        vehicleCat = item.vehicle_category,
        weaponCat = item.weapon_category,
        weaponRange = item.weapon_range,
        costNum = item.cost.quantity,
        costType = item.cost.unit,
        damageDice = item.damage?.damage_dice,
        dmgType = item.damage?.damage_type?.name,
        rngNorm =item.range?.normal,
        rngLong = item.range?.long,
        description = item.desc

    )
}