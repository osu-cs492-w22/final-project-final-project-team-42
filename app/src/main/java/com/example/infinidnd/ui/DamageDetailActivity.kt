package com.example.infinidnd.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android.githubsearchwithsettings.data.LoadingStatus
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

const val EXTRA_DAMAGE_DATA = "com.example.infinidnd.AllData"

class DamageDetailActivity : AppCompatActivity() {

    private var damageData: AllData? = null
    private lateinit var damageDescTV: TextView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private val viewModel: DamageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damage_detail)

        if (intent != null && intent.hasExtra(EXTRA_DAMAGE_DATA)) {
            damageData = intent.getSerializableExtra(EXTRA_DAMAGE_DATA) as AllData
            findViewById<TextView>(R.id.tv_damage_name).text = damageData!!.name

            damageDescTV = findViewById(R.id.tv_damage_desc)
            searchErrorTV = findViewById(R.id.tv_search_error)
            loadingIndicator = findViewById(R.id.loading_indicator)

            viewModel.searchResults.observe(this) { damageTypeDetails ->
                damageDescTV.text = damageTypeDetails?.desc?.get(0)
            }

            viewModel.loadingStatus.observe(this) { uiState ->
                when (uiState) {
                    LoadingStatus.LOADING -> {
                        loadingIndicator.visibility = View.VISIBLE
                        damageDescTV.visibility = View.INVISIBLE
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                    LoadingStatus.ERROR -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        damageDescTV.visibility = View.INVISIBLE
                        searchErrorTV.visibility = View.VISIBLE
                    }
                    else -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        damageDescTV.visibility = View.VISIBLE
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                }
            }

            viewModel.loadSearchResults(damageData!!.index)
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
        val intent: Intent = Uri.parse("https://www.dndbeyond.com/sources/basic-rules/combat#DamageTypes").let {
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