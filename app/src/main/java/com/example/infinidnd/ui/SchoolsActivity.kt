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
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator

class SchoolsActivity : AppCompatActivity() {

    private val allDataAdapter = AllDataAdapter(::onAllDataClick)
    private val viewModel: SchoolsViewModel by viewModels()

    private lateinit var searchResultsRV: RecyclerView
    private lateinit var searchBox: EditText
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var detailsNameTV: TextView
    private lateinit var detailsDescTV: TextView
    private lateinit var detailsIndexTV: TextView
    private lateinit var detailsUrlTV: TextView
    private lateinit var schoolDetails: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schools)

        searchBox = findViewById(R.id.schools_search_box)
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
        schoolDetails = findViewById(R.id.schools_details)

        schoolDetails.visibility = View.INVISIBLE
        schoolDetails.setOnClickListener {
            val schoolData = AllData(detailsNameTV.text.toString(), detailsIndexTV.text.toString(), detailsUrlTV.text.toString())
            val intent = Intent(this, SchoolDetailActivity::class.java).apply{
                putExtra(EXTRA_SCHOOL_DATA, schoolData)
            }
            startActivity(intent)
        }

        viewModel.allTypes.observe(this) { damageType ->
            allDataAdapter.updateAllDataList(damageType)
            schoolDetails.visibility = View.INVISIBLE
        }

        viewModel.searchResults.observe(this) { magicSchoolDetails ->
            Log.d("Damage Type Details", "$magicSchoolDetails")
            detailsNameTV.text = magicSchoolDetails?.name
            detailsDescTV.text = magicSchoolDetails?.desc
            detailsIndexTV.text = magicSchoolDetails?.index
            detailsUrlTV.text = magicSchoolDetails?.url

            schoolDetails.visibility = View.VISIBLE
        }

        viewModel.loadAllData("magic-schools")

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
            schoolDetails.visibility = View.INVISIBLE
        }

    }


    //TODO: change to schools detail activity
    private fun onAllDataClick(allData: AllData) {
        val intent = Intent(this, SchoolDetailActivity::class.java).apply {
            putExtra(EXTRA_SCHOOL_DATA, allData)
        }
        startActivity(intent)
    }
}