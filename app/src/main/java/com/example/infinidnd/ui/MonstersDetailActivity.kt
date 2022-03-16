package com.example.infinidnd.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android.githubsearchwithsettings.data.LoadingStatus
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar

const val EXTRA_MONSTER_DATA = "com.example.infinidnd.AllData"

class MonstersDetailActivity : AppCompatActivity() {

    private var monsterData: AllData? = null
    private lateinit var detailsLL: LinearLayout
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private val viewModel: MonstersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monsters_detail)

        if (intent != null && intent.hasExtra(EXTRA_DAMAGE_DATA)) {
            monsterData = intent.getSerializableExtra(EXTRA_DAMAGE_DATA) as AllData
            findViewById<TextView>(R.id.tv_monster_name).text = monsterData!!.name

            detailsLL = findViewById(R.id.ll_details)
            searchErrorTV = findViewById(R.id.tv_search_error)
            loadingIndicator = findViewById(R.id.loading_indicator)

            var temp :String
            viewModel.loadSearchResults(monsterData!!.index)
            viewModel.searchResults.observe(this) { damageTypeDetails ->
                temp = "Challenge Rating: " + damageTypeDetails?.cr.toString()
                findViewById<TextView>(R.id.tv_monster_cr).text = temp
                temp = damageTypeDetails?.size.toString() + " " + damageTypeDetails?.type
                findViewById<TextView>(R.id.tv_monster_size_type).text = temp
                temp = "Alignment: " + damageTypeDetails?.alignment
                findViewById<TextView>(R.id.tv_monster_alignment).text = temp
                temp = "\nHit Points: " + damageTypeDetails?.hp + " (" + damageTypeDetails?.hd +")"
                findViewById<TextView>(R.id.tv_monster_hp_hd).text = temp
                temp = "Armor Class: " + damageTypeDetails?.ac
                findViewById<TextView>(R.id.tv_monster_ac).text = temp

                findViewById<TextView>(R.id.tv_monster_stats_header).text ="\nMonster Stats: "
                temp = "Strength: " + damageTypeDetails?.str.toString()
                temp += "\nDexterity: " + damageTypeDetails?.dex.toString()
                temp += "\nConstitution: " + damageTypeDetails?.con.toString()
                temp += "\nIntelligence: " + damageTypeDetails?.int.toString()
                temp += "\nWisdom: " + damageTypeDetails?.wis.toString()
                temp += "\nCharisma: " + damageTypeDetails?.cha.toString()
                findViewById<TextView>(R.id.tv_monster_stats).text = temp

                temp = "\nMovement Speeds: "
                findViewById<TextView>(R.id.tv_monster_speed_header).text = temp
                temp = ""
                if(!damageTypeDetails?.spdWalk.isNullOrEmpty()){
                    temp +=  "Walking: " + damageTypeDetails?.spdWalk + "\n"
                }
                if(!damageTypeDetails?.spdSwim.isNullOrEmpty()){
                    temp +=  "Swimming: " + damageTypeDetails?.spdSwim + "\n"
                }
                if(!damageTypeDetails?.spdClimb.isNullOrEmpty()){
                    temp +=  "Climbing: " + damageTypeDetails?.spdClimb + "\n"
                }
                if(!damageTypeDetails?.spdFly.isNullOrEmpty()){
                    temp +=  "Flying: " + damageTypeDetails?.spdFly + "\n"
                }
                findViewById<TextView>(R.id.tv_monster_speed).text = temp


                if(!damageTypeDetails?.proficiencies.isNullOrEmpty()){
                    findViewById<TextView>(R.id.tv_monster_proficiencies_header).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_monster_proficiencies).visibility = View.VISIBLE
                    temp = "Proficiencies: "
                    findViewById<TextView>(R.id.tv_monster_proficiencies_header).text = temp
                    if (damageTypeDetails != null) {
                        temp = ""
                        for (i in damageTypeDetails.proficiencies) {
                            temp += i.proficiency.name.toString() + " + " + i.value.toString() + "\n"
                        }
                        findViewById<TextView>(R.id.tv_monster_proficiencies).text = temp
                    }
                }else{
                    findViewById<TextView>(R.id.tv_monster_proficiencies_header).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_monster_proficiencies).visibility = View.GONE
                }

                if(!damageTypeDetails?.dmgVulns.isNullOrEmpty()) {
                    findViewById<TextView>(R.id.tv_monster_vulnerabilities_header).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_monster_vulnerabilities).visibility = View.VISIBLE
                    temp = "Vulnerabilities: "
                    findViewById<TextView>(R.id.tv_monster_vulnerabilities_header).text = temp
                    if (damageTypeDetails != null) {
                        temp = ""
                        for (i in damageTypeDetails.dmgVulns) {
                            temp += i + "\n"
                        }
                        findViewById<TextView>(R.id.tv_monster_vulnerabilities).text = temp
                    }
                }else{
                    findViewById<TextView>(R.id.tv_monster_vulnerabilities_header).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_monster_vulnerabilities).visibility = View.GONE
                }

                if(!damageTypeDetails?.dmgResist.isNullOrEmpty()){
                    findViewById<TextView>(R.id.tv_monster_resistances_header).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_monster_resistances).visibility = View.VISIBLE
                    temp = "Resistances: "
                    findViewById<TextView>(R.id.tv_monster_resistances_header).text = temp
                    if (damageTypeDetails != null) {
                        temp = ""
                        for (i in damageTypeDetails.dmgResist) {
                            temp += i + "\n"
                        }
                        findViewById<TextView>(R.id.tv_monster_resistances).text = temp
                    }
                }else{
                    findViewById<TextView>(R.id.tv_monster_resistances_header).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_monster_resistances).visibility = View.GONE
                }

                if(!damageTypeDetails?.dmgImmune.isNullOrEmpty()){
                    findViewById<TextView>(R.id.tv_monster_immunities_header).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_monster_immunities).visibility = View.VISIBLE
                    temp = "Immunities: "
                    findViewById<TextView>(R.id.tv_monster_immunities_header).text = temp
                    if (damageTypeDetails != null) {
                        temp = ""
                        for (i in damageTypeDetails.dmgImmune) {
                            temp += i + "\n"
                        }
                        findViewById<TextView>(R.id.tv_monster_immunities).text = temp
                    }
                }else{
                    findViewById<TextView>(R.id.tv_monster_immunities_header).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_monster_immunities).visibility = View.GONE
                }

                temp = "Languages: "
                findViewById<TextView>(R.id.tv_monster_languages_header).text = temp
                temp = damageTypeDetails?.languages.toString() + "\n"
                if(temp == ""){
                    temp = "None \n"
                }
                findViewById<TextView>(R.id.tv_monster_languages).text = temp

                if(!damageTypeDetails?.sa.isNullOrEmpty()){
                    findViewById<TextView>(R.id.tv_monster_special_abilities).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_monster_special_abilities_header).visibility = View.VISIBLE
                    temp = "Special Abilities: "
                    findViewById<TextView>(R.id.tv_monster_special_abilities_header).text = temp
                    if (damageTypeDetails != null) {
                        temp = ""
                        for (i in damageTypeDetails.sa!!) {
                            temp += "\n" + i.name.toString() + ": " + i.desc.toString() + "\n"
                        }
                        findViewById<TextView>(R.id.tv_monster_special_abilities).text = temp
                    }
                }else{
                    findViewById<TextView>(R.id.tv_monster_special_abilities).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_monster_special_abilities_header).visibility = View.GONE
                }

                if(!damageTypeDetails?.act.isNullOrEmpty()){
                    findViewById<TextView>(R.id.tv_monster_actions).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_monster_actions_header).visibility = View.VISIBLE
                    temp = "Actions: "
                    findViewById<TextView>(R.id.tv_monster_actions_header).text = temp
                    if (damageTypeDetails != null) {
                        temp = ""
                        for (i in damageTypeDetails.act!!) {
                            temp += "\n" + i.name + ": " + i.desc + "\n"
                        }
                        findViewById<TextView>(R.id.tv_monster_actions).text = temp
                    }
                }else{
                    findViewById<TextView>(R.id.tv_monster_actions).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_monster_actions_header).visibility = View.GONE
                }

                if(!damageTypeDetails?.lAct.isNullOrEmpty()){
                    findViewById<TextView>(R.id.tv_monster_legendary_actions_header).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tv_monster_legendary_actions).visibility = View.VISIBLE
                    temp = "Legendary Actions: "
                    findViewById<TextView>(R.id.tv_monster_legendary_actions_header).text = temp
                    if (damageTypeDetails != null) {
                        temp = ""
                        for (i in damageTypeDetails.lAct!!) {
                            temp += "\n" + i.name + ": " + i.desc + "\n"
                        }
                        findViewById<TextView>(R.id.tv_monster_legendary_actions).text = temp
                    }
                }else{
                    findViewById<TextView>(R.id.tv_monster_legendary_actions_header).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_monster_legendary_actions).visibility = View.GONE
                }

            }

            viewModel.loadingStatus.observe(this) { uiState ->
                when (uiState) {
                    LoadingStatus.LOADING -> {
                        loadingIndicator.visibility = View.VISIBLE
                        detailsLL.visibility = View.GONE
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                    LoadingStatus.ERROR -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        detailsLL.visibility = View.GONE
                        searchErrorTV.visibility = View.VISIBLE
                    }
                    else -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        detailsLL.visibility = View.VISIBLE
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                }
            }

            viewModel.loadSearchResults(monsterData!!.index)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_dnd_beyond -> {
                viewOnWeb()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun viewOnWeb() {
        val intent: Intent = Uri.parse("https://www.dndbeyond.com/monsters").let {
            Intent(Intent.ACTION_VIEW, it)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(
                findViewById(R.id.coordinator_layout),
                "There's no app on this device that can open a web page...",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}