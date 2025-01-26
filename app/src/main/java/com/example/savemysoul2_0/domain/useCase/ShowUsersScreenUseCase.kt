package com.example.savemysoul2_0.domain.useCase

import com.example.savemysoul2_0.data.model.TelegramUser
import com.example.savemysoul2_0.domain.repo.TelegramUserRepo

class ShowUsersScreenUseCase(private val telegramUserRepo: TelegramUserRepo) {
    suspend fun getTelegramUsers(identifier: String): List<TelegramUser> {
        val response = telegramUserRepo.getTelegramUsers(identifier)
        return response
    }

    suspend fun updateTelegramUser(telegramUser: TelegramUser): String {
        val response = telegramUserRepo.updateTelegramUser(telegramUser)
        return response
    }

    suspend fun deleteTelegramUser(telegramUser: TelegramUser): String {
        val response = telegramUserRepo.deleteTelegramUser(telegramUser)
        return response
    }
}