package com.nieelz.danielstoryapp.repo

import android.content.Context

import com.nieelz.danielstoryapp.database.remote.retrofit.ApiConfig


object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val serviceAPI = ApiConfig.getApiService()

        return StoryRepository(serviceAPI)
    }
}