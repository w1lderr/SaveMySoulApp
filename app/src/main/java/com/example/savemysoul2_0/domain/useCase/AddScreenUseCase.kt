package com.example.savemysoul2_0.domain.useCase

import com.example.savemysoul2_0.data.model.TelegramUser
import com.example.savemysoul2_0.domain.repo.TelegramUserRepo

class AddScreenUseCase(private val telegramUserRepo: TelegramUserRepo) {
    suspend fun addTelegramUser(telegramUser: TelegramUser): String {
        val response = telegramUserRepo.addTelegramUser(telegramUser)
        return response
    }
}