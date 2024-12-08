package com.example.savemysoul.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.106:8080/savemysoul_api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}