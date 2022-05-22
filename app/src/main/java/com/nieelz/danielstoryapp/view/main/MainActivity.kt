package com.nieelz.danielstoryapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nieelz.danielstoryapp.R
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.databinding.ActivityMainBinding
import com.nieelz.danielstoryapp.view.ViewModelFactory
import com.nieelz.danielstoryapp.view.add.StoryActivity
import com.nieelz.danielstoryapp.view.detail.DetailActivity
import com.nieelz.danielstoryapp.view.maps.MapsActivity
import com.nieelz.danielstoryapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory(this) }
    private lateinit var binding: ActivityMainBinding
    private var currentToken = ""
    private lateinit var adapter2: StoryAdapter
    private var listStory = arrayListOf<ListStoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Story App"
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        mainViewModel.getLocalUser()


        mainViewModel.user.observe(this) {
            currentToken = it.token
            mainViewModel.story
            binding.nameTextView.text = getString(R.string.greeting, it.name)
        }

        binding.buttonPost.setOnClickListener {
            launcherAddStory.launch(Intent(this, StoryActivity::class.java))
        }

        populateDataStories()
        setContentView(binding.root)
        lifecycleScope.launch {
            adapter2.loadStateFlow.collect {
                if (it.refresh is LoadState.NotLoading) {
                    listStory.clear()
                    listStory.addAll(adapter2.snapshot().items)
                }
            }
        }
    }


    private fun populateDataStories() {
        adapter2 = StoryAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)

        mainViewModel.story.observe(this@MainActivity) {
            adapter2.submitData(lifecycle, it)

            adapter2.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ListStoryItem) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_STORY, data)
                    startActivity(intent)
                }

            })

        }
        binding.rvStory.adapter = adapter2

    }


    private val launcherAddStory = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) mainViewModel.story
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
                launcherAddStory.launch(Intent(this, MapsActivity::class.java)
                    .apply { putExtra("EXTRA_MAP", listStory) })
                true
            }
            else -> true
        }
    }


}