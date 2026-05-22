package com.example.mvvmarchitecture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.model.ItemDTO

// InventoryAdapter.kt

class InventoryAdapter(
    private val list: MutableList<ItemDTO>,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: ItemDTO, position: Int)
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtName = view.findViewById<TextView>(R.id.txtItemName)
        val txtDate = view.findViewById<TextView>(R.id.txtTransaction)
        val txtQty = view.findViewById<TextView>(R.id.txtQty)
        val txtShort = view.findViewById<TextView>(R.id.txtShortName)
        val ll_mainLayout = view.findViewById<LinearLayout>(R.id.ll_mainLayout)
        fun bind(item: ItemDTO) {
            txtName.text = item.name
            txtDate.text = "Last Transaction: ${item.time}"
            txtQty.text = "${item.availableQty} ${item.unit}"
            txtShort.text = item.name?.firstOrNull()?.toString()?.uppercase() ?: "I"

            // Set click listener on ll_mainLayout
            ll_mainLayout.setOnClickListener {
                onItemClickListener?.onItemClick(item, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]
        holder.bind(item)
    }
    fun updateItems(newItems: List<ItemDTO>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

}