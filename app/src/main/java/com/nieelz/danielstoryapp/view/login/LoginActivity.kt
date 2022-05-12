package com.nieelz.danielstoryapp.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.nieelz.danielstoryapp.databinding.ActivityLoginBinding
import com.nieelz.danielstoryapp.database.local.user.preferences.UserLogin
import com.nieelz.danielstoryapp.database.local.user.preferences.UserPreferences
import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.view.ViewModelFactory
import com.nieelz.danielstoryapp.view.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        loginButton()
    }

    private fun loginButton() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailTextInputLayout.error = "insert email"
                }
                password.isEmpty() -> {
                    binding.passwordTextInputLayout.error = "insert password"
                }
                else -> {
                    loginViewModel.login(BodyLogin(password, email))
                }
            }
        }
    }

    private fun viewModel() {
        loginViewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]

        loginViewModel.loginUser.observe(this) { user ->
            Toast.makeText(this, "Token = ${user.token}", Toast.LENGTH_SHORT).show()
            // save token here
            // loginViewModel.putToken(user.token)
            AlertDialog.Builder(this).apply {
                setTitle("Hi, ${user.name}!")
                setMessage("You have successfully logged in.")
                setPositiveButton("Continue") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }
    }
}