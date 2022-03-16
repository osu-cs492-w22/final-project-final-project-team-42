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
import org.w3c.dom.Text

const val EXTRA_EQUIPMENT_DATA = "com.example.infinidnd.AllData"

class EquipmentDetailActivity : AppCompatActivity() {

    private var equipmentData: AllData? = null
    private lateinit var detailsLL: LinearLayout
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private val viewModel: EquipmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment_detail)

        if (intent != null && intent.hasExtra(EXTRA_DAMAGE_DATA)) {
            equipmentData = intent.getSerializableExtra(EXTRA_EQUIPMENT_DATA) as AllData
            findViewById<TextView>(R.id.tv_equipment_name).text = equipmentData!!.name

            detailsLL = findViewById(R.id.ll_details)
            searchErrorTV = findViewById(R.id.tv_search_error)
            loadingIndicator = findViewById(R.id.loading_indicator)

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

                } else if(equipmentDetails?.equipmentCat == "Tools") {
                    findViewById<TextView>(R.id.tv_equipment_type).text =
                        equipmentDetails?.toolCat
                    findViewById<TextView>(R.id.tv_equipment_dmg_dice).text =
                        equipmentDetails?.description?.get(0)
                } else if(equipmentDetails?.equipmentCat == "Adventuring Gear"){
                    findViewById<TextView>(R.id.tv_equipment_type).text =
                        equipmentDetails?.gearCat
                    if(!equipmentDetails?.description.isNullOrEmpty()) {
                        findViewById<TextView>(R.id.tv_equipment_dmg_dice).text =
                            equipmentDetails?.description?.get(0)
                    }
                } else if(equipmentDetails?.equipmentCat == "Mounts and Vehicles"){
                    findViewById<TextView>(R.id.tv_equipment_type).text =
                        equipmentDetails?.vehicleCat
                    if(!equipmentDetails?.description.isNullOrEmpty()) {
                        findViewById<TextView>(R.id.tv_equipment_dmg_dice).text =
                            equipmentDetails?.description?.get(0)
                    }
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
        val intent: Intent = Uri.parse("https://www.dndbeyond.com/equipment").let {
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