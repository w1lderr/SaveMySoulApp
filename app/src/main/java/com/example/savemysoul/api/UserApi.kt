package com.example.savemysoul.api

import com.example.savemysoul.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("sendSOS")
    fun sendSOS(@Body user: User) : Call<User>
}