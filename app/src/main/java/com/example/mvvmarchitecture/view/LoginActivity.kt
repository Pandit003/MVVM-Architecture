package com.example.mvvmarchitecture.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmarchitecture.repository.LoginRepository
import com.example.mvvmarchitecture.databinding.ActivityLoginBinding
import com.example.mvvmarchitecture.services.RetrofitClient
import com.example.mvvmarchitecture.ui.LoginState
import com.example.mvvmarchitecture.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = LoginRepository(RetrofitClient.apiService)
        viewModel = LoginViewModel(repository)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.login(email, password)
        }

        observeLogin()
    }

    private fun observeLogin() {
        viewModel.loginState.observe(this) { state ->

            when (state) {
                is LoginState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is LoginState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    binding.tvName.text = "Wellcome "+state.profile.firstName
                    /*startActivity(Intent(this, MainActivity::class.java))
                    finish()*/
                }
                is LoginState.SessionActive -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Already Login", Toast.LENGTH_SHORT).show()
                    binding.tvName.text = "Wellcome "+state.profile.firstName

                    /*startActivity(Intent(this, MainActivity::class.java))
                    finish()*/
                }
                is LoginState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
}