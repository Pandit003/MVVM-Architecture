package com.example.mvvmarchitecture.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.databinding.ActivityLoginBinding
import com.example.mvvmarchitecture.databinding.ActivityMainBinding
import com.example.mvvmarchitecture.fragments.AnalysisFragment
import com.example.mvvmarchitecture.fragments.ExpenseFragment
import com.example.mvvmarchitecture.fragments.NeedsFragment
import com.example.mvvmarchitecture.repository.LoginRepository
import com.example.mvvmarchitecture.services.RetrofitClient
import com.example.mvvmarchitecture.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,0)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
        loadFragment(NeedsFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_needs -> loadFragment(NeedsFragment())
                R.id.action_exp -> loadFragment(ExpenseFragment())
                R.id.action_any -> loadFragment(AnalysisFragment())
            }
            true
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}