package com.nieelz.danielstoryapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.database.local.user.preferences.UserLogin
import com.nieelz.danielstoryapp.database.local.user.preferences.UserPreferences
import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.database.remote.retrofit.ApiService
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StoryRepository(private val serviceAPI: ApiService) {

    fun loginStory(bodyLogin: BodyLogin) = serviceAPI.loginStory(bodyLogin)
    fun registerStory(bodyRegister: BodyRegister) = serviceAPI.registerStory(bodyRegister)

}