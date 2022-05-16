package com.nieelz.danielstoryapp.database.local.user

data class UserLogin(
    val name: String,
    val userId: String,
    val token: String
)