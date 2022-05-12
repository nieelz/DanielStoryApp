package com.nieelz.danielstoryapp.database.remote.body

import com.google.gson.annotations.SerializedName

data class BodyRegister(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
