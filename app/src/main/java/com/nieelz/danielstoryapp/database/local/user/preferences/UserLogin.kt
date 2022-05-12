package com.nieelz.danielstoryapp.database.local.user.preferences

data class UserLogin(
    val name: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
)