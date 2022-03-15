package com.example.infinidnd.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

const val EXTRA_CONDITION_DATA = "com.example.infinidnd.AllData"

class ConditionsDetailActivity : AppCompatActivity() {

    private var conditionData: AllData? = null
    private val viewModel: ConditionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition_detail)

        if (intent != null && intent.hasExtra(EXTRA_CONDITION_DATA)) {
            conditionData = intent.getSerializableExtra(EXTRA_CONDITION_DATA) as AllData
            findViewById<TextView>(R.id.tv_name).text = conditionData!!.name

            viewModel.loadSearchResults(conditionData!!.index)

            viewModel.searchResults.observe(this) { conditionDetails ->
                var desc: String = ""
                if(!conditionDetails?.desc.isNullOrEmpty()) {
                    for (i in conditionDetails?.desc!!) {
                        desc += i + "\n"
                    }
                }
                findViewById<TextView>(R.id.tv_desc).text = desc
            }

        }
    }
}