package com.nieelz.danielstoryapp.view.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nieelz.danielstoryapp.database.local.user.UserLogin
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.repo.StoryRepository
import kotlinx.coroutines.launch


class MainViewModel(private val repository: StoryRepository) : ViewModel() {

    private var _user = MutableLiveData<UserLogin>()
    val user get() = _user

    val story: LiveData<PagingData<ListStoryItem>> = repository.getAllStories()

    fun getLocalUser() {
        viewModelScope.launch {
            repository.getLocalUser().collect {
                _user.value = it
            }
        }
    }

    fun logout() = viewModelScope.launch {
        repository.logout()
    }
}