package com.example.mvvmarchitecture.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmarchitecture.adapter.InventoryAdapter
import com.example.mvvmarchitecture.databinding.ActivitySearchBinding
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.viewmodel.StockViewModel
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(), InventoryAdapter.OnItemClickListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: StockViewModel
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[StockViewModel::class.java]

        setupRecyclerView()
        setupListeners()
        observeViewModel()

        viewModel.getStockItems()
    }

    private fun setupRecyclerView() {
        adapter = InventoryAdapter(mutableListOf(), this)
        binding.recyclerSearchResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerSearchResults.adapter = adapter
    }

    private fun setupListeners() {
        // Handle Back Arrow click inside the box
        binding.tilSearch.setStartIconOnClickListener {
            finish()
        }

        // Search text watcher
        binding.etSearchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.filteredItems.collect { list ->
                adapter.updateItems(list)
            }
        }
    }

    override fun onItemClick(item: ReceivedItemDTO, position: Int) {
        // Handle item click
    }
}
