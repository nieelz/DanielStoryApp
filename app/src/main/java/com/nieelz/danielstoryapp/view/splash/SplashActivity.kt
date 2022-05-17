package com.nieelz.danielstoryapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.nieelz.danielstoryapp.databinding.ActivitySplashBinding
import com.nieelz.danielstoryapp.view.ViewModelFactory
import com.nieelz.danielstoryapp.view.main.MainActivity
import com.nieelz.danielstoryapp.view.welcome.WelcomeActivity
import com.nieelz.danielstoryapp.view.welcome.WelcomeViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val welcomeViewModel : WelcomeViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        welcomeViewModel.getUserLocal()
        welcomeViewModel.isLogin.observe(this){
            Handler(mainLooper).postDelayed({
                val target = if (it) MainActivity::class.java else WelcomeActivity::class.java
                startActivity(Intent(this, target))
                finish()
            }, 1000L)
        }
    }
}