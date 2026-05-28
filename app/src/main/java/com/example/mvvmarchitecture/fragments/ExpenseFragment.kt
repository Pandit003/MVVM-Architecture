package com.example.mvvmarchitecture.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.adapter.ExpenseAdapter
import com.example.mvvmarchitecture.adapter.InventoryAdapter
import com.example.mvvmarchitecture.model.ExpenseItemDTO
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.ui.NeedsState
import com.example.mvvmarchitecture.utils.AddExpenseBottomSheet
import com.example.mvvmarchitecture.utils.AddItemBottomSheet
import com.example.mvvmarchitecture.utils.common
import com.example.mvvmarchitecture.viewmodel.ExpenseViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class ExpenseFragment : Fragment(R.layout.fragment_expense), ExpenseAdapter.OnExpenseItemClickListener {

    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter
    private lateinit var recyclerExpense: RecyclerView
    private var availableItemsList: List<ExpenseItemDTO> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        recyclerExpense = view.findViewById(R.id.recyclerExpense)
        val fabAdd = view.findViewById<ExtendedFloatingActionButton>(R.id.fabAddExpense)

        adapter = ExpenseAdapter(mutableListOf(), this)
        recyclerExpense.layoutManager = LinearLayoutManager(requireContext())
        recyclerExpense.adapter = adapter

        viewModel.getNamesAndUnit()
        viewModel.getAllExpenseItems()

        fabAdd.setOnClickListener {
            AddExpenseBottomSheet(availableItemsList, ExpenseItemDTO()) { expenseData ->
                viewModel.addExpense(expenseData)
            }.show(parentFragmentManager, "AddExpense")
        }

        // Observe the list of expenses (for the recycler view)
        lifecycleScope.launch {
            viewModel.expenses.collect { list ->
                adapter.updateItems(list)
            }
        }

        // Keep track of available items for the bottom sheet suggestions
        lifecycleScope.launch {
            viewModel.availableItems.collect { list ->
                availableItemsList = list
            }
        }

        observeState()
    }

    private fun observeState() {
        viewModel.expenseState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NeedsState.Success -> {
                    Toast.makeText(requireContext(), "Expense Added Successfully", Toast.LENGTH_SHORT).show()
                }
                is NeedsState.Failure -> {
                    common().alertDialog(requireContext(), "Error", state.message)
                }
                is NeedsState.Exception -> {
                    common().alertDialog(requireContext(), "Warning", state.message)
                }
                else -> {}
            }
        }
    }

    override fun onExpenseItemClick(
        item: List<ExpenseItemDTO>,
        position: Int
    ) {
        AddExpenseBottomSheet(item,item[position]) { updatedData ->
            Log.d("ItemUpdate", "Updated: ${updatedData.ItemName}")
            updatedData.expenseId = item[position].expenseId
            updatedData.itemId = item[position].itemId
            viewModel.addExpense(updatedData)
        }.show(parentFragmentManager, "Update Item")
    }

}
