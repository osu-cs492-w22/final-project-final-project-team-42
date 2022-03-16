package com.example.infinidnd.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData
import com.google.android.material.snackbar.Snackbar

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
                val abilityScore = "Ability Score: "+featDetails?.abilityScore
                val minScore = "Minimum Score: " + featDetails?.minScore.toString()
                findViewById<TextView>(R.id.tv_desc).text = desc
                findViewById<TextView>(R.id.tv_ability_score).text = abilityScore
                findViewById<TextView>(R.id.tv_min_score).text = minScore
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