//package com.example.infinidnd.ui
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.infinidnd.R
//import com.example.infinidnd.data.DamageType
//
//class DamageTypeListAdapter(private val onDamageTypeClick: (DamageType) -> Unit)
//    : RecyclerView.Adapter<AllDataAdapter.AllDataViewHolder>() {
//    var damageTypeList = listOf<DamageType>()
//
//    fun updateDamageList(newDamageList: List<DamageType>?) {
//        damageTypeList = newDamageList ?: listOf()
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount() = damageTypeList.size
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DamageTypeViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.damage_type_list_item, parent, false)
//        return DamageTypeViewHolder(itemView, onDamageTypeClick)
//    }
//
//    override fun onBindViewHolder(holder: DamageTypeViewHolder, position: Int) {
//        holder.bind(damageTypeList[position])
//    }
//
//    class DamageTypeViewHolder(itemView: View, val onClick: (DamageType) -> Unit)
//        : RecyclerView.ViewHolder(itemView) {
//        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
//        private val indexTV: TextView = itemView.findViewById(R.id.tv_index)
//        private val urlTV: TextView = itemView.findViewById(R.id.tv_index)
//        private var currentDamageType: DamageType? = null
//
//        init {
//            itemView.setOnClickListener {
//                currentDamageType?.let(onClick)
//            }
//        }
//
//        fun bind(damageType: DamageType) {
//            currentDamageType = damageType
//            nameTV.text = damageType.name
//            indexTV.text = damageType.index
//            urlTV.text = damageType.url
//        }
//    }
//}