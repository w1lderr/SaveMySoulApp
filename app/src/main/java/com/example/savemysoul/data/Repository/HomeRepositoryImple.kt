package com.example.savemysoul.data.Repository

import com.example.savemysoul.api.RetrofitService
import com.example.savemysoul.data.Models.User
import com.example.savemysoul.domain.Repository.HomeRepository

class HomeRepositoryImple: HomeRepository {
    private val userApi = RetrofitService().getUserApi()

    override suspend fun sendSOS(user: User): String {
        return try {
            val response = userApi.sendSOS(user)
            if (response.isSuccessful) {
                "Успшіно відправлено користувача з айді: ${user.id}"
            } else {
                "Помилка: ${response.errorBody()}"
            }
        } catch (e: Exception) {
            "Помилка: ${e.localizedMessage}"
        }
    }
}