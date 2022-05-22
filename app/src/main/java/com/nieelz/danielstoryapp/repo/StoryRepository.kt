package com.nieelz.danielstoryapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.nieelz.danielstoryapp.database.local.user.UserLogin
import com.nieelz.danielstoryapp.database.local.user.UserPreferences
import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.database.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val serviceAPI: ApiService,
    private val userPreferences: UserPreferences
) {

    var token: String? = null


    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(serviceAPI, token!!)
            }
        ).liveData
    }


    private fun getAuthData(): LiveData<UserLogin> {
        return userPreferences.getUser().asLiveData()
    }
    init{
        getAuthData().observeForever{
            token = it.token
        }
    }


    fun loginStory(bodyLogin: BodyLogin) = serviceAPI.loginStory(bodyLogin)
    fun registerStory(bodyRegister: BodyRegister) = serviceAPI.registerStory(bodyRegister)
    fun postStory(token: String, file: MultipartBody.Part, description: RequestBody)
    = serviceAPI.postStory(token, file, description)

    suspend fun saveUserToLocal(userLogin: UserLogin) = userPreferences.saveUser(userLogin)
    fun getLocalUser() = userPreferences.getUser()
    suspend fun logout() = userPreferences.logout()
}