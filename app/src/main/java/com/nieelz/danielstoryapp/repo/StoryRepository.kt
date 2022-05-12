package com.nieelz.danielstoryapp.repo

import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.database.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class StoryRepository(private val serviceAPI: ApiService) {

    fun loginStory(bodyLogin: BodyLogin) = serviceAPI.loginStory(bodyLogin)
    fun registerStory(bodyRegister: BodyRegister) = serviceAPI.registerStory(bodyRegister)

//    fun getAllStory(token: String): Flow<States<List<Story>>> =
//        remoteDataSource.getAllStory(token)

}