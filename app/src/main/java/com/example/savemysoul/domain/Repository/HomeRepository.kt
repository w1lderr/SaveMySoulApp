package com.example.savemysoul.domain.Repository

import com.example.savemysoul.data.Models.User

interface HomeRepository {
    suspend fun sendSOS(user: User): String
}