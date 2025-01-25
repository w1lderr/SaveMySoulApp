package com.example.savemysoul2_0.data.model

import androidx.annotation.NonNull

data class TelegramUser(
    val id: Long? = null,

    @NonNull
    val identifier: String = "",

    @NonNull
    val telegramId: String = "",

    @NonNull
    val telegramMessage: String = ""
)