package com.nieelz.danielstoryapp.database.local.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {


    fun getUser(): Flow<UserLogin> {
        return dataStore.data.map { preferences ->
            UserLogin(
                preferences[NAME_KEY] ?: "",
                preferences[USER_ID_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
            )
        }
    }

    suspend fun saveUser(user: UserLogin) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[USER_ID_KEY] = user.userId
            preferences[TOKEN_KEY] = user.token
        }
    }


    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[USER_ID_KEY] = ""
            preferences[TOKEN_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        private val NAME_KEY = stringPreferencesKey("name_key")
        private val USER_ID_KEY = stringPreferencesKey("user_id_key")
        private val TOKEN_KEY = stringPreferencesKey("token_key")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}