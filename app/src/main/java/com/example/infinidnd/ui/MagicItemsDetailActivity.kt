package com.example.infinidnd.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

const val EXTRA_MAGIC_ITEM_DATA = "com.example.infinidnd.AllData"

class MagicItemsDetailActivity : AppCompatActivity() {

    private var magicItemData: AllData? = null
    private lateinit var detailsLL: LinearLayout
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private val viewModel: MagicItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_item_detail)

        if (intent != null && intent.hasExtra(EXTRA_MAGIC_ITEM_DATA)) {
            magicItemData = intent.getSerializableExtra(EXTRA_MAGIC_ITEM_DATA) as AllData
            findViewById<TextView>(R.id.tv_name).text = magicItemData!!.name

            detailsLL = findViewById(R.id.ll_details)
            searchErrorTV = findViewById(R.id.tv_search_error)
            loadingIndicator = findViewById(R.id.loading_indicator)

            viewModel.loadSearchResults(magicItemData!!.index)

            viewModel.searchResults.observe(this) { magicItemDetails ->
                var desc: String = ""
                if(!magicItemDetails?.desc.isNullOrEmpty()) {
                    for (i in magicItemDetails?.desc!!) {
                        desc += i + "\n"
                    }
                }
                findViewById<TextView>(R.id.tv_desc).text = desc
                val equipCat = "Equipment Category: " +  magicItemDetails?.equipmentCategory
                findViewById<TextView>(R.id.tv_equipment_category).text = equipCat

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
        val intent: Intent = Uri.parse("https://www.dndbeyond.com/magic-items").let {
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