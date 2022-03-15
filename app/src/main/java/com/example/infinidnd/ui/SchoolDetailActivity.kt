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

const val EXTRA_SCHOOL_DATA = "com.example.infinidnd.AllData"

class SchoolDetailActivity : AppCompatActivity() {

    private var schoolData: AllData? = null
    private val viewModel: SchoolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_detail)

        if (intent != null && intent.hasExtra(EXTRA_SCHOOL_DATA)) {
            schoolData = intent.getSerializableExtra(EXTRA_SCHOOL_DATA) as AllData
            findViewById<TextView>(R.id.tv_name).text = schoolData!!.name

            viewModel.searchResults.observe(this) { magicSchoolDetails ->
                findViewById<TextView>(R.id.tv_desc).text = magicSchoolDetails?.desc
            }

            viewModel.loadSearchResults(schoolData!!.index)
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
        val intent: Intent = Uri.parse("https://www.dndbeyond.com/classes/wizard#SchoolofAbjuration").let {
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