package com.nieelz.danielstoryapp.repo

import android.content.Context

import androidx.datastore.preferences.preferencesDataStoreFile
import com.nieelz.danielstoryapp.database.local.user.preferences.UserPreferences
import com.nieelz.danielstoryapp.database.remote.retrofit.ApiConfig
import com.nieelz.danielstoryapp.repo.StoryRepository


object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val serviceAPI = ApiConfig.getApiService()
        return StoryRepository(serviceAPI)
    }
}