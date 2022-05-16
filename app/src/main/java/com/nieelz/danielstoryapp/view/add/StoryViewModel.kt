package com.nieelz.danielstoryapp.view.add

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.database.remote.response.FileUploadResponse
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.database.remote.response.StoryResponse
import com.nieelz.danielstoryapp.repo.StoryRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {

    private var _stories = MutableLiveData<List<ListStoryItem>>()
    val stories get() = _stories

    fun postStory(file: MultipartBody.Part, description : String) {
        viewModelScope.launch {
            val descriptionB = description.toRequestBody("text/plain".toMediaType())
            repository.uploadImage(file, descriptionB).enqueue(object : Callback<FileUploadResponse> {
                override fun onResponse(
                    call: Call<FileUploadResponse>,
                    response: Response<FileUploadResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            Log.d("TAG", "onResponse: success = ${response.body()?.message}")
                        }
                    } else {
                        Log.d("TAG", "onResponse: failed = ${response.body()?.message}")
                    }
                }
                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    Log.d("TAG", "onFailure: ${t.message}")
                }
            })
        }


    }
}

