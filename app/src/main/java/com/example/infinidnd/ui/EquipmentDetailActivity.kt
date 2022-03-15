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
                findViewById<TextView>(R.id.tv_equipment_type).text = equipmentDetails?.weaponCat
//                findViewById<TextView>(R.id.tv_equipment_range).text = equipmentDetails?.weaponRange
//                findViewById<TextView>(R.id.tv_equipment_cost_num).text = equipmentDetails?.costNum.toString()
//                findViewById<TextView>(R.id.tv_equipment_cost_type).text = equipmentDetails?.costType
//                findViewById<TextView>(R.id.tv_equipment_dmg_dice).text = equipmentDetails?.damageDice
//                findViewById<TextView>(R.id.tv_equipment_dmg_type).text = equipmentDetails?.dmgType
//                findViewById<TextView>(R.id.tv_equipment_rng_norm).text = equipmentDetails?.rngNorm.toString()
//                findViewById<TextView>(R.id.tv_equipment_rng_long).text = equipmentDetails?.rngLong?.toString()
//                var properties = ""
//                if(!equipmentDetails?.property.isNullOrEmpty()) {
//                    for (i in equipmentDetails?.property!!) {
//                        properties += i + "\n"
//                    }
                //}
                //findViewById<TextView>(R.id.tv_equipment_properties).text = properties
            }


        }
    }
}