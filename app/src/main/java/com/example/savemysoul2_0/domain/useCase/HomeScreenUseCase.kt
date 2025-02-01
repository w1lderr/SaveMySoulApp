package com.example.savemysoul2_0.domain.useCase

import com.example.savemysoul2_0.data.model.TelegramUserService
import com.example.savemysoul2_0.data.repo.TelegramUserServiceRepo
import javax.inject.Inject

class HomeScreenUseCase @Inject constructor(private val telegramUserServiceRepo: TelegramUserServiceRepo) {
    suspend fun sendSOS(telegramUserService: TelegramUserService): String {
        val response = telegramUserServiceRepo.sendSOS(telegramUserService)
        return response
    }
}