package com.example.infinidnd.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.infinidnd.R
import com.example.infinidnd.data.AllData

class AllDataAdapter(private val onAllDataClick: (AllData) -> Unit)
    : RecyclerView.Adapter<AllDataAdapter.AllDataViewHolder>() {
    var allDataList = listOf<AllData>()

    fun updateAllDataList(newDamageList: List<AllData>?) {
        allDataList = newDamageList ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = allDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDataViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.damage_type_list_item, parent, false)
        return AllDataViewHolder(itemView, onAllDataClick)
    }

    override fun onBindViewHolder(holder: AllDataViewHolder, position: Int) {
        holder.bind(allDataList[position])
    }

    class AllDataViewHolder(itemView: View, val onClick: (AllData) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val indexTV: TextView = itemView.findViewById(R.id.tv_index)
        private val urlTV: TextView = itemView.findViewById(R.id.tv_index)
        private var currentAllData: AllData? = null

        init {
            itemView.setOnClickListener {
                currentAllData?.let(onClick)
            }
        }

        fun bind(alLData: AllData) {
            currentAllData = alLData
            nameTV.text = alLData.name
            indexTV.text = alLData.index
            urlTV.text = alLData.url
        }
    }
}