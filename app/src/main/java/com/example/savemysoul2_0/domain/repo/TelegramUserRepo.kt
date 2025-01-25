package com.example.savemysoul2_0.domain.repo

import com.example.savemysoul2_0.data.model.TelegramUser

interface TelegramUserRepo {
    suspend fun addTelegramUser(telegramUser: TelegramUser): String
    suspend fun updateTelegramUser(telegramUser: TelegramUser): String
    suspend fun deleteTelegramUser(telegramUser: TelegramUser): String
    suspend fun getTelegramUsers(identifier: String): List<TelegramUser>
}