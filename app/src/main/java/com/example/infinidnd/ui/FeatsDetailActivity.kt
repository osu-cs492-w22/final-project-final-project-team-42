package com.example.infinidnd.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

const val EXTRA_FEAT_DATA = "com.example.infinidnd.AllData"

class FeatsDetailActivity : AppCompatActivity() {

    private var featData: AllData? = null
    private val viewModel: FeatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feat_detail)

        if (intent != null && intent.hasExtra(EXTRA_FEAT_DATA)) {
            featData = intent.getSerializableExtra(EXTRA_FEAT_DATA) as AllData
            findViewById<TextView>(R.id.tv_name).text = featData!!.name

            viewModel.loadSearchResults(featData!!.index)

            viewModel.searchResults.observe(this) { featDetails ->
                var desc: String = ""
                if(!featDetails?.desc.isNullOrEmpty()) {
                    for (i in featDetails?.desc!!) {
                        desc += i + "\n"
                    }
                }
                findViewById<TextView>(R.id.tv_desc).text = desc
                findViewById<TextView>(R.id.tv_ability_score).text = featDetails?.abilityScore
                findViewById<TextView>(R.id.tv_min_score).text = featDetails?.minScore.toString()
            }

        }
    }
}