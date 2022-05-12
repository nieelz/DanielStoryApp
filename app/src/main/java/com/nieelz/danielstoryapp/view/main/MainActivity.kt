package com.nieelz.danielstoryapp.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.nieelz.danielstoryapp.R
import com.nieelz.danielstoryapp.databinding.ActivityMainBinding
import com.nieelz.danielstoryapp.database.local.user.preferences.UserPreferences
import com.nieelz.danielstoryapp.repo.StoryRepository
import com.nieelz.danielstoryapp.view.ViewModelFactory
import com.nieelz.danielstoryapp.view.add.StoryActivity
import com.nieelz.danielstoryapp.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: StoryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        binding.buttonPost.setOnClickListener {
            startActivity(Intent(this, StoryActivity::class.java))
        }
    }

    private fun viewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(this))[MainViewModel::class.java]

//        mainViewModel.use().observe(this) { user ->
//            if (user.isLogin) {
//                binding.nameTextView.text = getString(R.string.greeting, user.name)
//            } else {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_menu -> {
//                mainViewModel.logout()
                true
            }
            R.id.send_menu ->{
                startActivity(Intent(this, StoryActivity::class.java))
                true
            }
            else -> true
        }
    }

}