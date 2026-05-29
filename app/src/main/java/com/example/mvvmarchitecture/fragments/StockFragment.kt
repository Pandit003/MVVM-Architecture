package com.example.mvvmarchitecture.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.adapter.StockAdapter
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.ui.NeedsState
import com.example.mvvmarchitecture.utils.common
import com.example.mvvmarchitecture.viewmodel.StockViewModel
import kotlinx.coroutines.launch

class StockFragment : Fragment(R.layout.fragment_analysis), StockAdapter.OnItemClickListener {

    private lateinit var viewModel: StockViewModel
    private lateinit var adapter: StockAdapter
    private lateinit var recyclerStock: RecyclerView
    private lateinit var progressStock: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[StockViewModel::class.java]

        recyclerStock = view.findViewById(R.id.recyclerStock)
        progressStock = view.findViewById(R.id.progressStock)

        adapter = StockAdapter(mutableListOf(), this)
        recyclerStock.layoutManager = LinearLayoutManager(requireContext())
        recyclerStock.adapter = adapter

        viewModel.getStockItems()

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressStock.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { list ->
                adapter.updateItems(list)
            }
        }

        viewModel.stockState.observe(viewLifecycleOwner) { state ->
            when (state) {
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

    override fun onItemClick(item: ReceivedItemDTO, position: Int) {
        // Implement logic if you want to show details or edit an item from the stock list
    }
}
