package com.example.infinidnd.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.infinidnd.R
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinidnd.data.AllData
import com.example.infinidnd.data.DamageType
import com.example.infinidnd.data.DamageTypeDetails
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator

class DamageActivity : AppCompatActivity() {

    private val allDataAdapter = AllDataAdapter(::onAllDataClick)
    private val viewModel: DamageViewModel by viewModels()

    private lateinit var searchResultsRV: RecyclerView
    private lateinit var searchBox: EditText
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var detailsNameTV: TextView
    private lateinit var detailsDescTV: TextView
    private lateinit var detailsIndexTV: TextView
    private lateinit var damageDetails: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damage)

        searchBox = findViewById(R.id.damage_search_box)
        searchResultsRV = findViewById(R.id.rv_search_results)
        searchErrorTV = findViewById(R.id.tv_search_error)
        loadingIndicator = findViewById(R.id.loading_indicator)

        searchResultsRV.layoutManager = LinearLayoutManager(this)
        searchResultsRV.setHasFixedSize(true)

        searchResultsRV.adapter = allDataAdapter

        detailsNameTV = findViewById(R.id.tv_damage_name)
        detailsDescTV = findViewById(R.id.tv_damage_desc)
        detailsIndexTV = findViewById(R.id.tv_damage_index)
        damageDetails = findViewById(R.id.damage_details)

        damageDetails.visibility = View.INVISIBLE

        viewModel.allTypes.observe(this) { damageType ->
            allDataAdapter.updateAllDataList(damageType)
            damageDetails.visibility = View.INVISIBLE
        }

        viewModel.searchResults.observe(this) { damageTypeDetails ->

            Log.d("Damage Type Details", "$damageTypeDetails")
            detailsNameTV.text = damageTypeDetails?.name
            damageDetails.visibility = View.VISIBLE
        }

        viewModel.loadAllData("damage-types")

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
            damageDetails.visibility = View.INVISIBLE
        }
    }

    private fun onAllDataClick(allData: AllData) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun onDamageTypeDetailsClick(damageType: DamageTypeDetails) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}