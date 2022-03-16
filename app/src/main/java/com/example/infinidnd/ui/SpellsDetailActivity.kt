package com.example.infinidnd.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

const val EXTRA_SPELLS_DATA = "com.example.infinidnd.AllData"

class SpellsDetailActivity : AppCompatActivity() {

    private var spellData: AllData? = null
    private val viewModel: SpellsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spells_detail)

        if (intent != null && intent.hasExtra(EXTRA_SPELLS_DATA)) {
            spellData = intent.getSerializableExtra(EXTRA_SPELLS_DATA) as AllData
            findViewById<TextView>(R.id.tv_spell_name).text = spellData!!.name

            viewModel.searchResults.observe(this) { spellDetails ->
                var temp: String
                temp = ""
                spellDetails?.description?.forEach { i ->
                    temp += "$i \n"
                }
                if(!spellDetails?.higherLvl.isNullOrEmpty()){
                    temp += spellDetails?.higherLvl?.get(0)
                }

                findViewById<TextView>(R.id.tv_spell_description_higher_level).text = temp
                temp = "Range: "
                findViewById<TextView>(R.id.tv_spell_range_header).text = temp
                temp = spellDetails?.range.toString()
                findViewById<TextView>(R.id.tv_spell_range).text = temp

                temp = "\nComponents: "
                findViewById<TextView>(R.id.tv_spell_component_header).text = temp
                temp = ""
                if (spellDetails != null) {
                    for(i in spellDetails.components){
                        temp += "$i "
                    }
                }
                findViewById<TextView>(R.id.tv_spell_component).text = temp

                if(spellDetails?.material != null) {
                    findViewById<TextView>(R.id.tv_spell_material_header).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_spell_material).visibility = View.VISIBLE
                    temp = "\nMaterials: "
                    findViewById<TextView>(R.id.tv_spell_material_header).text = temp
                    temp = spellDetails?.material.toString()
                    findViewById<TextView>(R.id.tv_spell_material).text = temp
                }else{
                    findViewById<TextView>(R.id.tv_spell_material_header).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_spell_material).visibility = View.GONE
                }

                temp = "\nRitual Casting: "
                findViewById<TextView>(R.id.tv_spell_ritual_header).text = temp
                    temp = if(spellDetails?.ritual == true){
                        "Allowed"
                    }else{
                        "Not Allowed"
                    }
                findViewById<TextView>(R.id.tv_spell_ritual).text = temp

                temp = "\nSpell Duration: "
                findViewById<TextView>(R.id.tv_spell_duration_header).text = temp
                temp = spellDetails?.duration.toString()
                findViewById<TextView>(R.id.tv_spell_duration).text = temp

                temp = if(spellDetails?.concentration == true){
                    "Concentration"
                }else{
                    "Non-Concentration"
                }
                findViewById<TextView>(R.id.tv_spell_concentration_header).text = temp

                temp = "\nCast Time: "
                findViewById<TextView>(R.id.tv_spell_cast_time_header).text = temp
                temp = spellDetails?.castTime.toString()
                findViewById<TextView>(R.id.tv_spell_cast_time).text = temp

                temp = "\nSpell Level: "
                findViewById<TextView>(R.id.tv_spell_level_header).text = temp
                temp = spellDetails?.lvl.toString()
                findViewById<TextView>(R.id.tv_spell_level).text = temp

                temp = "\nSpell School: "
                findViewById<TextView>(R.id.tv_spell_school_header).text = temp
                temp = spellDetails?.school.toString()
                findViewById<TextView>(R.id.tv_spell_school).text = temp

                temp = "\nLearnable By: "
                findViewById<TextView>(R.id.tv_spell_classes_header).text = temp
                temp = "Classes: \n"
                if (spellDetails != null) {
                    for(i in spellDetails.classes)
                        temp += i.name + "\n"
                }
                if (spellDetails != null) {
                    if(!spellDetails.subclasses.isNullOrEmpty()) {
                        temp += "\nSubclasses: \n"
                        for (i in spellDetails.subclasses)
                            temp += i.name + "\n"
                    }
                }
                findViewById<TextView>(R.id.tv_spell_classes).text = temp


            }

            viewModel.loadSearchResults(spellData!!.index)
        }
    }
}