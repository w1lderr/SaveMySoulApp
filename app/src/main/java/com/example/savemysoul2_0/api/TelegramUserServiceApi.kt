package com.example.savemysoul2_0.api

import com.example.savemysoul2_0.data.model.TelegramUserService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TelegramUserServiceApi {
    @POST("sendSOS")
    suspend fun sendSOS(@Body telegramUserService: TelegramUserService): Response<String>
}