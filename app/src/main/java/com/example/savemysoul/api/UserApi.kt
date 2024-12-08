package com.example.savemysoul.api

import com.example.savemysoul.data.Models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("sendSOS")
    suspend fun sendSOS(@Body user: User) : Response<Unit>
}