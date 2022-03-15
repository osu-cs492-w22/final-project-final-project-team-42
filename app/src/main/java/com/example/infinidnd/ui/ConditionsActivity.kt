package com.example.infinidnd.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator

class ConditionsActivity : AppCompatActivity() {

    private val allDataAdapter = AllDataAdapter(::onAllDataClick)
    private val viewModel: ConditionsViewModel by viewModels()

    private lateinit var searchResultsRV: RecyclerView
    private lateinit var searchBox: AutoCompleteTextView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var detailsNameTV: TextView
    private lateinit var detailsDescTV: TextView
    private lateinit var detailsIndexTV: TextView
    private lateinit var detailsUrlTV: TextView
    private lateinit var conditionDetails: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conditions)

        searchBox = findViewById(R.id.condition_search_box)
        searchResultsRV = findViewById(R.id.rv_search_results)
        searchErrorTV = findViewById(R.id.tv_search_error)
        loadingIndicator = findViewById(R.id.loading_indicator)

        searchResultsRV.layoutManager = LinearLayoutManager(this)
        searchResultsRV.setHasFixedSize(true)
        searchResultsRV.adapter = allDataAdapter

        detailsNameTV = findViewById(R.id.tv_name)
        detailsDescTV = findViewById(R.id.tv_desc)
        detailsIndexTV = findViewById(R.id.tv_index)
        detailsUrlTV = findViewById(R.id.tv_url)
        conditionDetails = findViewById(R.id.condition_details)

        conditionDetails.visibility = View.INVISIBLE
        conditionDetails.setOnClickListener {
            val conditionData = AllData(detailsNameTV.text.toString(), detailsIndexTV.text.toString(), detailsUrlTV.text.toString())
            val intent = Intent(this, ConditionsDetailActivity::class.java).apply{
                putExtra(EXTRA_CONDITION_DATA, conditionData)
            }
            startActivity(intent)
        }

        viewModel.allTypes.observe(this) { condition ->
            allDataAdapter.updateAllDataList(condition)
            conditionDetails.visibility = View.INVISIBLE
        }

        viewModel.searchResults.observe(this) { ConditionDetails ->
            Log.d("Damage Type Details", "$ConditionDetails")
            detailsNameTV.text = ConditionDetails?.name
            var desc = ""
            if(!ConditionDetails?.desc.isNullOrEmpty()) {
                for (i in ConditionDetails?.desc!!)
                    desc += i + "\n"
            }
            detailsDescTV.text = desc
            detailsIndexTV.text = ConditionDetails?.index
            detailsUrlTV.text = ConditionDetails?.url

            conditionDetails.visibility = View.VISIBLE
        }

        viewModel.nameList.observe(this) { nameList ->
            if(!nameList.isNullOrEmpty()) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1, nameList
                )
                searchBox.setAdapter(adapter)
            }
        }
        viewModel.loadAllData("conditions")

        // onclick for search button - hides overall results, loads
        val searchBtn: Button = findViewById(R.id.btn_search)
        searchBtn.setOnClickListener {
            val query = searchBox.text.toString()
            if (!TextUtils.isEmpty(query)) {
                searchResultsRV.visibility = View.INVISIBLE

                Log.d("Sending query", "${query}")
                viewModel.loadSearchResults(query)
            }
        }

        val clearBtn: Button = findViewById(R.id.btn_clear)
        clearBtn.setOnClickListener {
            searchBox.text.clear()
            searchResultsRV.visibility = View.VISIBLE
            conditionDetails.visibility = View.INVISIBLE
        }

    }

    private fun onAllDataClick(allData: AllData) {
        val intent = Intent(this,ConditionsDetailActivity::class.java).apply {
            putExtra(EXTRA_CONDITION_DATA, allData)
        }
        startActivity(intent)
    }
}