package com.nieelz.danielstoryapp.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nieelz.danielstoryapp.database.local.story.ListStoryItem
import com.nieelz.danielstoryapp.repo.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: StoryRepository): ViewModel() {

    private val _story = MutableLiveData<List<ListStoryItem>>()

    fun story(): LiveData<List<ListStoryItem>> {
        return _story
    }

//    fun setUserData(query: String) {
//        StoryRepository.getAllStory(token).enqueue(object : Callback<StoryResponse> {
//            override fun onResponse(
//                call: Call<StoryResponse>,
//                response: Response<StoryResponse>
//            ) {
//
//                if (response.isSuccessful && response.body() != null) {
//                    _story.value = response.body()?.items
//                } else {
//                    Log.e("RIP", "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                Log.e("RIP", "onFailure: ${t.message}")
//            }
//        })
//    }

}