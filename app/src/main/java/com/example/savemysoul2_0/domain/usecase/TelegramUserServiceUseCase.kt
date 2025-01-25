package com.example.savemysoul2_0.domain.usecase

import com.example.savemysoul2_0.data.model.TelegramUserService
import com.example.savemysoul2_0.domain.repo.TelegramUserServiceRepo

class TelegramUserServiceUseCase(private val telegramUserServiceRepo: TelegramUserServiceRepo) {
    suspend fun sendSOS(telegramUserService: TelegramUserService): String {
        val response = telegramUserServiceRepo.sendSOS(telegramUserService)
        return response
    }
}