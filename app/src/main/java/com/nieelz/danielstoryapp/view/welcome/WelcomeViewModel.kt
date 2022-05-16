package com.nieelz.danielstoryapp.view.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nieelz.danielstoryapp.repo.StoryRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WelcomeViewModel(private val repository: StoryRepository): ViewModel() {

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin get() = _isLogin

    fun getUserLocal(){
        viewModelScope.launch {
            repository.getLocalUser().collect {
                _isLogin.value = it.token.isNotBlank()
            }
        }
    }
}