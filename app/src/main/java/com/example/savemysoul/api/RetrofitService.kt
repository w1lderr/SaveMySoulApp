package com.example.savemysoul.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.199:8080/savemysoul_api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}