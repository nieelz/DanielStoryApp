package com.nieelz.danielstoryapp.view.main


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.database.remote.response.StoryResponse
import com.nieelz.danielstoryapp.database.local.user.UserLogin
import com.nieelz.danielstoryapp.repo.StoryRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(private val repository: StoryRepository) : ViewModel() {

    private var _user = MutableLiveData<UserLogin>()
    val user get() = _user

    private var _stories = MutableLiveData<List<ListStoryItem>>()
    val stories get() = _stories

    fun getAllStories(token: String) {
        viewModelScope.launch {
            try {
                repository.getAllStories(token).enqueue(object : Callback<StoryResponse> {
                    override fun onResponse(
                        call: Call<StoryResponse>,
                        response: Response<StoryResponse>
                    ) {
                        if (response.isSuccessful) response.body()?.listStory?.let {
                            _stories.value = it
                        } else Log.d("TAG", "onResponse: failed = ${response.body()?.message}")
                    }

                    override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                        Log.d("TAG", "onFailure: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                Log.d("TAG", "onFailure: ${e.message}")
            }
        }
    }


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