package com.example.infinidnd.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearchwithsettings.data.LoadingStatus
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData
import com.example.infinidnd.data.FeatDetails
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar

class FeatsActivity : AppCompatActivity() {

    private val allDataAdapter = AllDataAdapter(::onAllDataClick)
    private val viewModel: FeatsViewModel by viewModels()

    private lateinit var searchResultsRV: RecyclerView
    private lateinit var searchBox: AutoCompleteTextView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var detailsNameTV: TextView
    private lateinit var detailsDescTV: TextView
    private lateinit var detailsIndexTV: TextView
    private lateinit var detailsUrlTV: TextView
    private lateinit var featDetails: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feats)

        searchBox = findViewById(R.id.feat_search_box)
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
        featDetails = findViewById(R.id.feat_details)

        featDetails.visibility = View.INVISIBLE
        featDetails.setOnClickListener {
            val featData = AllData(detailsNameTV.text.toString(), detailsIndexTV.text.toString(), detailsUrlTV.text.toString())
            val intent = Intent(this, FeatsDetailActivity::class.java).apply{
                putExtra(EXTRA_FEAT_DATA, featData)
            }
            startActivity(intent)
        }

        viewModel.allTypes.observe(this) { damageType ->
            allDataAdapter.updateAllDataList(damageType)
            featDetails.visibility = View.INVISIBLE
        }

        viewModel.searchResults.observe(this) { FeatsDetails ->
            Log.d("Damage Type Details", "$FeatsDetails")
            detailsNameTV.text = FeatsDetails?.name
            var desc = ""
            if(!FeatsDetails?.desc.isNullOrEmpty()) {
                for (i in FeatsDetails?.desc!!)
                    desc += i + "\n"
            }
            detailsDescTV.text = desc
            detailsIndexTV.text = FeatsDetails?.index
            detailsUrlTV.text = FeatsDetails?.url

            featDetails.visibility = View.VISIBLE
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

        viewModel.loadAllData("feats")

        viewModel.loadingStatus.observe(this) { uiState ->
            when (uiState) {
                LoadingStatus.LOADING -> {
                    loadingIndicator.visibility = View.VISIBLE
                    searchResultsRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                    featDetails.visibility = View.GONE
                }
                LoadingStatus.ERROR -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    searchResultsRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.VISIBLE
                    featDetails.visibility = View.GONE
                }
                else -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    if(detailsNameTV.text.isNullOrEmpty()) {
                        searchResultsRV.visibility = View.VISIBLE
                    }
                    searchErrorTV.visibility = View.INVISIBLE
                }
            }
        }

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
            featDetails.visibility = View.INVISIBLE
            searchErrorTV.visibility = View.INVISIBLE
        }

    }

    private fun onAllDataClick(allData: AllData) {
        val intent = Intent(this,FeatsDetailActivity::class.java).apply {
            putExtra(EXTRA_FEAT_DATA, allData)
        }
        startActivity(intent)
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
        val intent: Intent = Uri.parse("https://www.dndbeyond.com/feats").let {
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