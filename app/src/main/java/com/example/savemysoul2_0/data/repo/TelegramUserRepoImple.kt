package com.example.savemysoul2_0.data.repo

import com.example.savemysoul2_0.api.TelegramUserController
import com.example.savemysoul2_0.data.model.TelegramUser
import com.example.savemysoul2_0.domain.repo.TelegramUserRepo

class TelegramUserRepoImple: TelegramUserRepo {
    private val telegramUserApi = TelegramUserController().getTelegramUserApi()

    override suspend fun addTelegramUser(telegramUser: TelegramUser): String {
        return try {
            val response = telegramUserApi.addTelegramUser(telegramUser)
            if (response.isSuccessful) {
                response.message()
            } else {
                "Помилка: ${response.errorBody()}"
            }
        } catch (e: Exception) {
            "Помилка: ${e.localizedMessage}"
        }
    }

    override suspend fun updateTelegramUser(telegramUser: TelegramUser): String {
        return try {
            val response = telegramUserApi.updateTelegramUser(telegramUser)
            if (response.isSuccessful) {
                response.message()
            } else {
                "Помилка: ${response.errorBody()}"
            }
        } catch (e: Exception) {
            "Помилка: ${e.localizedMessage}"
        }
    }

    override suspend fun deleteTelegramUser(telegramUser: TelegramUser): String {
        return try {
            val response = telegramUserApi.deleteTelegramUser(telegramUser)
            if (response.isSuccessful) {
                response.message()
            } else {
                "Помилка: ${response.errorBody()}"
            }
        } catch (e: Exception) {
            "Помилка: ${e.localizedMessage}"
        }
    }

    override suspend fun getTelegramUsers(identifier: String): List<TelegramUser> {
        val listOfTelegramUsers: List<TelegramUser> = telegramUserApi.getTelegramUsers(identifier) as List<TelegramUser>
        return listOfTelegramUsers
    }
}