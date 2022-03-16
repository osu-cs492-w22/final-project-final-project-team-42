package com.example.infinidnd.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

const val EXTRA_EQUIPMENT_DATA = "com.example.infinidnd.AllData"

class EquipmentDetailActivity : AppCompatActivity() {

    private var equipmentData: AllData? = null
    private val viewModel: EquipmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment_detail)

        if (intent != null && intent.hasExtra(EXTRA_DAMAGE_DATA)) {
            equipmentData = intent.getSerializableExtra(EXTRA_EQUIPMENT_DATA) as AllData
            findViewById<TextView>(R.id.tv_equipment_name).text = equipmentData!!.name

            viewModel.loadSearchResults(equipmentData!!.index)
            viewModel.searchResults.observe(this) { equipmentDetails ->
                Log.d("Equipment Details:", equipmentDetails.toString())
                var temp: String
                temp = "Cost: " + equipmentDetails?.costNum.toString() + equipmentDetails?.costType
                findViewById<TextView>(R.id.tv_equipment_cost_num).text = temp
                if(equipmentDetails?.equipmentCat == "Weapon") {
                    temp = "Weapon Type: " + equipmentDetails?.weaponCat
                    findViewById<TextView>(R.id.tv_equipment_type).text = temp

                    temp = " Weapon Range: " + equipmentDetails?.weaponRange
                    findViewById<TextView>(R.id.tv_equipment_range).text = temp

                    temp = "Damage: " + equipmentDetails?.damageDice + " " + equipmentDetails?.dmgType
                    findViewById<TextView>(R.id.tv_equipment_dmg_dice).text = temp

                    temp = equipmentDetails?.rngNorm.toString() + "/" + equipmentDetails?.rngLong.toString()
                    findViewById<TextView>(R.id.tv_equipment_rng_norm).text = temp

                }
                if(equipmentDetails?.equipmentCat == "Tools"){
                    findViewById<TextView>(R.id.tv_equipment_type).text =
                        equipmentDetails?.toolCat
                    findViewById<TextView>(R.id.tv_equipment_dmg_dice).text = equipmentDetails?.description?.get(0)
                }
                if(equipmentDetails?.equipmentCat == "Adventuring Gear"){
                    findViewById<TextView>(R.id.tv_equipment_type).text =
                        equipmentDetails?.gearCat
                }
                if(equipmentDetails?.equipmentCat == "Mounts and Vehicles"){
                    findViewById<TextView>(R.id.tv_equipment_type).text =
                        equipmentDetails?.vehicleCat
                    if(!equipmentDetails?.description.isNullOrEmpty()) {
                        findViewById<TextView>(R.id.tv_equipment_dmg_dice).text =
                            equipmentDetails?.description?.get(0)
                    }
                }
            }


        }
    }
}