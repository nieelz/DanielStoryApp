package com.nieelz.danielstoryapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.nieelz.danielstoryapp.R
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.databinding.ActivityMainBinding
import com.nieelz.danielstoryapp.view.ViewModelFactory
import com.nieelz.danielstoryapp.view.add.StoryActivity
import com.nieelz.danielstoryapp.view.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory(this) }
    private lateinit var binding: ActivityMainBinding
    private var currentToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Story App"
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getLocalUser()

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.user.observe(this) {
            currentToken = it.token
            mainViewModel.getAllStories(it.token)
            binding.nameTextView.text = getString(R.string.greeting, it.name)
        }

        mainViewModel.stories.observe(this) {
            populateDataStories(it)
        }

        binding.buttonPost.setOnClickListener {
            launcherAddStory.launch(Intent(this, StoryActivity::class.java))
        }
    }


    private fun populateDataStories(it: List<ListStoryItem>) {
        binding.rvStory.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = StoryAdapter(this@MainActivity, it)
        }
    }

    private val launcherAddStory = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) mainViewModel.getAllStories(currentToken)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_menu -> {
                mainViewModel.logout().also {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                }
                true
            }
            R.id.send_menu -> {
                launcherAddStory.launch(Intent(this, StoryActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}