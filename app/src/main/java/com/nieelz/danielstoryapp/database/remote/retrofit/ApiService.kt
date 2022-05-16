package com.nieelz.danielstoryapp.database.remote.retrofit

import com.nieelz.danielstoryapp.database.remote.response.StoryResponse
import com.nieelz.danielstoryapp.database.remote.body.BodyLogin
import com.nieelz.danielstoryapp.database.remote.body.BodyRegister
import com.nieelz.danielstoryapp.database.remote.response.FileUploadResponse
import com.nieelz.danielstoryapp.database.remote.response.LoginResponse
import com.nieelz.danielstoryapp.database.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @POST("/v1/login")
    fun loginStory(@Body bodyLogin: BodyLogin): Call<LoginResponse>

    @POST("/v1/register")
    fun registerStory(@Body body: BodyRegister): Call<RegisterResponse>

    @GET("/v1/stories")
    fun getAllStory(@Header("Authorization") header: String): Call<StoryResponse>

    @Multipart
    @POST("/v1/stories/guest")
    fun postImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>

    @Multipart
    @POST("/v1/stories")
    suspend fun postStory(
        @Header("Authorization") header: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<RegisterResponse>

}