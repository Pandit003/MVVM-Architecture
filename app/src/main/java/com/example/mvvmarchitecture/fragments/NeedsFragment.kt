package com.example.mvvmarchitecture.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.utils.AddItemBottomSheet
import com.example.mvvmarchitecture.viewmodel.NeedsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class NeedsFragment : Fragment(R.layout.fragment_needs) {

    private lateinit var viewModel: NeedsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NeedsViewModel::class.java]

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
                Log.d("NeedsFragment", "Items: $list")
            }
        }
    }
}