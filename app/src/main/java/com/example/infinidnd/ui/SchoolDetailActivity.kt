package com.example.infinidnd.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

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
}