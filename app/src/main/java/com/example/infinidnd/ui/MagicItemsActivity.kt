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

class MagicItemsActivity : AppCompatActivity() {

    private val allDataAdapter = AllDataAdapter(::onAllDataClick)
    private val viewModel: MagicItemsViewModel by viewModels()

    private lateinit var searchResultsRV: RecyclerView
    private lateinit var searchBox: AutoCompleteTextView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var detailsNameTV: TextView
    private lateinit var detailsDescTV: TextView
    private lateinit var detailsIndexTV: TextView
    private lateinit var detailsUrlTV: TextView
    private lateinit var magicItemDetails: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_items)

        searchBox = findViewById(R.id.magic_item_search_box)
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
        magicItemDetails = findViewById(R.id.magic_item_details)

        magicItemDetails.visibility = View.INVISIBLE
        magicItemDetails.setOnClickListener {
            val magicItemData = AllData(detailsNameTV.text.toString(), detailsIndexTV.text.toString(), detailsUrlTV.text.toString())
            val intent = Intent(this, MagicItemsDetailActivity::class.java).apply{
                putExtra(EXTRA_MAGIC_ITEM_DATA, magicItemData)
            }
            startActivity(intent)
        }

        viewModel.allTypes.observe(this) { magicItem ->
            allDataAdapter.updateAllDataList(magicItem)
            magicItemDetails.visibility = View.INVISIBLE
        }

        viewModel.searchResults.observe(this) { MagicItemsDetails ->
            Log.d("Damage Type Details", "$MagicItemsDetails")
            detailsNameTV.text = MagicItemsDetails?.name
            var desc = ""
            if(!MagicItemsDetails?.desc.isNullOrEmpty()) {
                for (i in MagicItemsDetails?.desc!!)
                    desc += i + "\n"
            }
            detailsDescTV.text = desc
            detailsIndexTV.text = MagicItemsDetails?.index
            detailsUrlTV.text = MagicItemsDetails?.url

            magicItemDetails.visibility = View.VISIBLE
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
        viewModel.loadAllData("magic-items")

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
            magicItemDetails.visibility = View.INVISIBLE
        }

    }

    private fun onAllDataClick(allData: AllData) {
        val intent = Intent(this,MagicItemsDetailActivity::class.java).apply {
            putExtra(EXTRA_MAGIC_ITEM_DATA, allData)
        }
        startActivity(intent)
    }
}