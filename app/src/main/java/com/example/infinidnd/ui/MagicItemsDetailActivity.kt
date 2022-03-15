package com.example.infinidnd.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

const val EXTRA_MAGIC_ITEM_DATA = "com.example.infinidnd.AllData"

class MagicItemsDetailActivity : AppCompatActivity() {

    private var magicItemData: AllData? = null
    private val viewModel: MagicItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_item_detail)

        if (intent != null && intent.hasExtra(EXTRA_MAGIC_ITEM_DATA)) {
            magicItemData = intent.getSerializableExtra(EXTRA_MAGIC_ITEM_DATA) as AllData
            findViewById<TextView>(R.id.tv_name).text = magicItemData!!.name

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
        }
    }
}