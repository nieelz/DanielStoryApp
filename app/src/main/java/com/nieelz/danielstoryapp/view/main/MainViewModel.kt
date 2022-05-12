package com.nieelz.danielstoryapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.database.local.user.preferences.UserLogin
import com.nieelz.danielstoryapp.database.local.user.preferences.UserPreferences
import com.nieelz.danielstoryapp.repo.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StoryRepository): ViewModel() {

}