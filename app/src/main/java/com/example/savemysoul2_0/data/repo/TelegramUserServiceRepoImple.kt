package com.example.savemysoul2_0.data.repo

import com.example.savemysoul2_0.api.TelegramUserServiceController
import com.example.savemysoul2_0.data.model.TelegramUserService
import com.example.savemysoul2_0.domain.repo.TelegramUserServiceRepo

class TelegramUserServiceRepoImple: TelegramUserServiceRepo {
    private val telegramUserServiceApi = TelegramUserServiceController().getTelegramUserServiceApi()

    override suspend fun sendSOS(telegramUserService: TelegramUserService): String {
        return try {
            val response = telegramUserServiceApi.sendSOS(telegramUserService)
            if (response.isSuccessful) {
                response.message()
            } else {
                "Помилка: ${response.errorBody()}"
            }
        } catch (e: Exception) {
            "Помилка: ${e.localizedMessage}"
        }
    }
}