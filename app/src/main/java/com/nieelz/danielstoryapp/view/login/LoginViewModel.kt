package com.nieelz.danielstoryapp.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.database.local.user.UserLogin
import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.database.remote.response.LoginResponse
import com.nieelz.danielstoryapp.database.remote.response.LoginResult
import com.nieelz.danielstoryapp.repo.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val repository: StoryRepository) : ViewModel() {


    private val _loginUser = MutableLiveData<LoginResult>()
    val loginUser: LiveData<LoginResult> = _loginUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(bodyLogin: BodyLogin) = viewModelScope.launch {
        _isLoading.value = true
        repository.loginStory(bodyLogin).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        _loginUser.value = it.loginResult
                        Log.d("TAG", "onResponse: success ${it.loginResult}")
                    }
                } else {
                    Log.d("TAG", "onResponse: failed = ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("TAG", "onResponse: onFailure = ${t.message}")
            }
        })
    }

    fun saveDataUserToLocal(userLogin: UserLogin) = viewModelScope.launch {
        repository.saveUserToLocal(userLogin)
    }

}