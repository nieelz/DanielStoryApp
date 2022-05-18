package com.nieelz.danielstoryapp.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nieelz.danielstoryapp.database.local.user.UserPreferences

import com.nieelz.danielstoryapp.database.remote.retrofit.ApiConfig


object Injection {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun provideRepository(context: Context): StoryRepository {
        val serviceAPI = ApiConfig.getApiService()
        return StoryRepository(serviceAPI, UserPreferences.getInstance(context.dataStore))
    }
}