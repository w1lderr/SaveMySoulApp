package com.example.savemysoul2_0.api

import com.example.savemysoul2_0.data.model.TelegramUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TelegramUserApi {
    @POST("addTelegramUser")
    suspend fun addTelegramUser(@Body telegramUser: TelegramUser): Response<Unit>

    @PUT("updateTelegramUser")
    suspend fun updateTelegramUser(@Body telegramUser: TelegramUser): Response<String>

    @POST("deleteTelegramUser")
    suspend fun deleteTelegramUser(@Body telegramUser: TelegramUser): Response<String>

    @GET("getTelegramUsers")
    suspend fun getTelegramUsers(@Query("identifier") identifier: String): List<TelegramUser>
}