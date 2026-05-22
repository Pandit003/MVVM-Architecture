package com.example.mvvmarchitecture.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.adapter.InventoryAdapter
import com.example.mvvmarchitecture.model.ItemDTO
import com.example.mvvmarchitecture.ui.LoginState
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NeedsViewModel::class.java]
        recyclerInventory = view.findViewById(R.id.recyclerInventory)
        adapter = InventoryAdapter(mutableListOf(),this)
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

        // Observe data
        lifecycleScope.launch {
            viewModel.items.collect { list ->
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
        item: ItemDTO,
        position: Int
    ) {
        AddItemBottomSheet(item) { updatedData ->
            Log.d("ItemUpdate", "Updated: ${updatedData.name}")
            updatedData.isUpdate = "1"
            updatedData.id = item.id
            updatedData.itemId = item.id
            viewModel.addItem(updatedData)
        }.show(parentFragmentManager, "Update Item")
    }
}