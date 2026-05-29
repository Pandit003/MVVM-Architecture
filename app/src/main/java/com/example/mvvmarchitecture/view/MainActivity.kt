package com.example.mvvmarchitecture.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.databinding.ActivityMainBinding
import com.example.mvvmarchitecture.fragments.StockFragment
import com.example.mvvmarchitecture.fragments.ExpenseFragment
import com.example.mvvmarchitecture.fragments.NeedsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )

        // Default: Load NeedsFragment and hide search
        binding.ivSearch.visibility = View.GONE
        loadFragment(NeedsFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_needs -> {
                    binding.ivSearch.visibility = View.GONE
                    loadFragment(NeedsFragment())
                }
                R.id.action_exp -> {
                    binding.ivSearch.visibility = View.VISIBLE
                    loadFragment(ExpenseFragment())
                }
                R.id.action_any -> {
                    binding.ivSearch.visibility = View.VISIBLE
                    loadFragment(StockFragment())
                }
            }
            true
        }

        binding.ivSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
