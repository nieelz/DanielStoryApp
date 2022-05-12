package com.nieelz.danielstoryapp.view.register

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.nieelz.danielstoryapp.databinding.ActivityRegisterBinding
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.view.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        registerButton()
    }

    private fun registerButton() {
        binding.buttonDaftar.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameTextInputLayout.error = "insert name"
                }
//                email.isEmpty() -> {
//                    binding.emailTextInputLayout.error = "insert email"
//                }
//                password.isEmpty() -> {
//                    binding.passwordTextInputLayout.error = "insert password"
//                }password.length < 6 -> {
//                    binding.passwordTextInputLayout.error = "password less than 6"
//                }
                else -> {
                    registerViewModel.register(BodyRegister(password, name, email))
                    AlertDialog.Builder(this).apply {
                        setTitle("Hello!")
                        setMessage("Your account has been successfully registered")
                        setPositiveButton("Continue") { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun viewModel() {
        registerViewModel = ViewModelProvider(this, ViewModelFactory(this))[RegisterViewModel::class.java]


    }
}