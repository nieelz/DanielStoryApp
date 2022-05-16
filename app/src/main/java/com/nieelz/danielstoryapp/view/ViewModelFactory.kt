package com.nieelz.danielstoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nieelz.danielstoryapp.repo.Injection
import com.nieelz.danielstoryapp.view.add.StoryViewModel
import com.nieelz.danielstoryapp.view.login.LoginViewModel
import com.nieelz.danielstoryapp.view.main.MainViewModel
import com.nieelz.danielstoryapp.view.register.RegisterViewModel
import com.nieelz.danielstoryapp.view.welcome.WelcomeViewModel


class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(Injection.provideRepository(context)) as T
            }


            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }
}