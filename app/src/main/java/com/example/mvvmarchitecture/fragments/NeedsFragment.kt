package com.example.mvvmarchitecture.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.adapter.InventoryAdapter
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.ui.NeedsState
import com.example.mvvmarchitecture.utils.AddItemBottomSheet
import com.example.mvvmarchitecture.utils.common
import com.example.mvvmarchitecture.viewmodel.NeedsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class NeedsFragment : Fragment(R.layout.fragment_needs), InventoryAdapter.OnItemClickListener {

    private lateinit var viewModel: NeedsViewModel
    private lateinit var adapter: InventoryAdapter
    private lateinit var recyclerInventory: RecyclerView
    private lateinit var edtSearch: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NeedsViewModel::class.java]
        recyclerInventory = view.findViewById(R.id.recyclerInventory)
        edtSearch = view.findViewById(R.id.edtSearch)
        adapter = InventoryAdapter(mutableListOf(), this)
        recyclerInventory.layoutManager = LinearLayoutManager(requireContext())
        recyclerInventory.adapter = adapter
        viewModel.getAllItems()

        val fab = view.findViewById<FloatingActionButton>(R.id.fabAdd)

        // Open BottomSheet
        fab.setOnClickListener {
            AddItemBottomSheet { data ->
                viewModel.addItem(data)
            }.show(parentFragmentManager, "AddItem")
        }

        // Setup Search Listener
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Observe filtered data
        lifecycleScope.launch {
            viewModel.filteredItems.collect { list ->
                adapter.updateItems(list)
            }
        }
        observeItems()
    }
    private fun observeItems() {
        viewModel.needState.observe(viewLifecycleOwner) { state ->

            when (state) {
                is NeedsState.Success -> {
                    Toast.makeText(requireContext(), "Item Added", Toast.LENGTH_SHORT).show()
                }

                is NeedsState.Failure -> {
                    common().alertDialog(requireContext(), "Error", state.message)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }

                is NeedsState.Exception -> {
                    common().alertDialog(requireContext(), "Warning", state.message)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    override fun onItemClick(
        item: ReceivedItemDTO,
        position: Int
    ) {
        AddItemBottomSheet(item) { updatedData ->
            Log.d("ItemUpdate", "Updated: ${updatedData.ItemName}")
            updatedData.isUpdate = "1"
            updatedData.ReceiveId = item.ReceiveId
            updatedData.itemId = item.itemId
            viewModel.addItem(updatedData)
        }.show(parentFragmentManager, "Update Item")
    }
}