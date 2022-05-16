package com.nieelz.danielstoryapp.repo

import com.nieelz.danielstoryapp.database.local.user.UserLogin
import com.nieelz.danielstoryapp.database.local.user.UserPreferences
import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.database.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class StoryRepository(private val serviceAPI: ApiService, private val userPreferences: UserPreferences) {


    fun getAllStories(token: String) = serviceAPI.getAllStory(token)
    fun loginStory(bodyLogin: BodyLogin) = serviceAPI.loginStory(bodyLogin)
    fun registerStory(bodyRegister: BodyRegister) = serviceAPI.registerStory(bodyRegister)

    suspend fun saveUserToLocal(userLogin: UserLogin) = userPreferences.saveUser(userLogin)
    fun getLocalUser() = userPreferences.getUser()
    suspend fun logout() = userPreferences.logout()

}