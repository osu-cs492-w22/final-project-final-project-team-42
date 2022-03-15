package com.example.infinidnd.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

class MonstersDetailActivity : AppCompatActivity() {

    private var damageData: AllData? = null
    private val viewModel: MonstersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damage_detail)

        if (intent != null && intent.hasExtra(EXTRA_DAMAGE_DATA)) {
            damageData = intent.getSerializableExtra(EXTRA_DAMAGE_DATA) as AllData
            findViewById<TextView>(R.id.tv_damage_name).text = damageData!!.name

            viewModel.searchResults.observe(this) { damageTypeDetails ->
                findViewById<TextView>(R.id.tv_damage_desc).text = damageTypeDetails?.desc?.get(0)
            }

            viewModel.loadSearchResults(damageData!!.index)
        }
    }
}