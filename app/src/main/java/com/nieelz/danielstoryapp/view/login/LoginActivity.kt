package com.nieelz.danielstoryapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nieelz.danielstoryapp.database.local.user.UserLogin
import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.databinding.ActivityLoginBinding
import com.nieelz.danielstoryapp.view.ViewModelFactory
import com.nieelz.danielstoryapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        loginButton()
        playAnimation()
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


        loginViewModel.loginUser.observe(this) { user ->
//            Toast.makeText(this, "Token = ${user.token}", Toast.LENGTH_SHORT).show()
            loginViewModel.saveDataUserToLocal(
                UserLogin(
                    user.name,
                    user.userId,
                    "Bearer " + user.token
                )
            )

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


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView3, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.buttonLogin, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 500
        }.start()
    }


}