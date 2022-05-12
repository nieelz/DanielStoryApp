package com.nieelz.danielstoryapp.view.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.database.remote.response.RegisterResponse
import com.nieelz.danielstoryapp.repo.StoryRepository
import retrofit2.Callback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {



    fun register(bodyRegister: BodyRegister) = viewModelScope.launch{
            repository.registerStory(bodyRegister).enqueue(object : Callback<RegisterResponse>{
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let {
                            Log.d("TAG", "onResponse: success ${it.message}")
                        }
                    } else {
                        Log.d("TAG", "onResponse: failed = ${response.body()?.message}")
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.d("TAG", "onResponse: onFailure = ${t.message}")
                }
            })
        }
}