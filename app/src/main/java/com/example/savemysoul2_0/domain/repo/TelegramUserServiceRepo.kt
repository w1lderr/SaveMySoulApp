package com.example.savemysoul2_0.domain.repo

import com.example.savemysoul2_0.data.model.TelegramUserService

interface TelegramUserServiceRepo {
    suspend fun sendSOS(telegramUserService: TelegramUserService): String
}