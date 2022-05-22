package com.nieelz.danielstoryapp.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.repo.StoryRepository

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    val story: LiveData<PagingData<ListStoryItem>> = repository.getAllStories()
}