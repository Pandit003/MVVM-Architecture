package com.example.mvvmarchitecture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.model.ExpenseItemDTO

// InventoryAdapter.kt

class ExpenseAdapter(
    private val list: MutableList<ExpenseItemDTO>,
    private val onItemClickListener: OnExpenseItemClickListener? = null
) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {
    interface OnExpenseItemClickListener {
        fun onExpenseItemClick(item: List<ExpenseItemDTO>, position: Int)
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtItemName = view.findViewById<TextView>(R.id.txtItemName)
//        val txtCategory = view.findViewById<TextView>(R.id.txtCategory)
//        val txtDescription = view.findViewById<TextView>(R.id.txtDescription)
        val txtExpenseQty = view.findViewById<TextView>(R.id.txtExpenseQty)
//        val txtIssuedBy = view.findViewById<TextView>(R.id.txtIssuedBy)
        val txtExpenseTime = view.findViewById<TextView>(R.id.txtExpenseTime)
        val txtBalance = view.findViewById<TextView>(R.id.txtBalance)
        val btnEdit = view.findViewById<ImageView>(R.id.btnEdit)
//        val btnDelete = view.findViewById<ImageView>(R.id.btnDelete)
        val ll_mainLayout = view.findViewById<LinearLayout>(R.id.ll_mainLayout)
        fun bind(item: ExpenseItemDTO) {
            txtItemName.text = item.ItemName
            txtExpenseTime.text = "${item.expenseTime}"
            txtBalance.text = "${item.availableQty} ${item.unit}"
            txtExpenseQty.text = "${item.expenseQty} ${item.unit}"
           /* txtDescription.text = item.description
            txtIssuedBy.text = item.issuedTo
            txtCategory.text = item.category*/

            // Set click listener on ll_mainLayout
            btnEdit.setOnClickListener {
                onItemClickListener?.onExpenseItemClick(list, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_inventory, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]
        holder.bind(item)
    }
    fun updateItems(newItems: List<ExpenseItemDTO>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

}