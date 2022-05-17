package com.nieelz.danielstoryapp.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.database.remote.response.RegisterResponse
import com.nieelz.danielstoryapp.repo.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {

    private var _isUserCreated = MutableLiveData<Boolean>()
    val isUserCreated get() = _isUserCreated

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(bodyRegister: BodyRegister) = viewModelScope.launch {
        _isLoading.value = true
        repository.registerStory(bodyRegister).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isUserCreated.value = response.isSuccessful
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("TAG", "onResponse: onFailure = ${t.message}")
            }
        })
    }
}